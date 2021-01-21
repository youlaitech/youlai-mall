package com.youlai.mall.oms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.core.base.Query;
import com.youlai.common.core.result.Result;
import com.youlai.common.core.result.ResultCode;
import com.youlai.common.mybatis.utils.PageUtils;
import com.youlai.common.web.exception.BizException;
import com.youlai.common.web.util.BeanMapperUtils;
import com.youlai.common.web.util.WebUtils;
import com.youlai.mall.oms.dao.OrderDao;
import com.youlai.mall.oms.dao.OrderDeliveryDao;
import com.youlai.mall.oms.dao.OrderGoodsDao;
import com.youlai.mall.oms.dao.OrderLogsDao;
import com.youlai.mall.oms.enums.OrderStatusEnum;
import com.youlai.mall.oms.enums.OrderTypeEnum;
import com.youlai.mall.oms.pojo.entity.OrderDeliveryEntity;
import com.youlai.mall.oms.pojo.entity.OrderEntity;
import com.youlai.mall.oms.pojo.entity.OrderGoodsEntity;
import com.youlai.mall.oms.pojo.vo.*;
import com.youlai.mall.oms.service.CartService;
import com.youlai.mall.oms.service.OrderService;
import com.youlai.mall.pms.api.ProductFeignService;
import com.youlai.mall.pms.pojo.vo.SkuInfoVO;
import com.youlai.mall.pms.pojo.vo.SkuStockVO;
import com.youlai.mall.pms.pojo.vo.WareSkuStockVO;
import com.youlai.mall.ums.api.MemberFeignService;
import com.youlai.mall.ums.pojo.dto.UmsAddressDTO;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    private CartService cartService;

    private ProductFeignService productFeignService;

    private MemberFeignService memberFeignService;

    private AsyncTaskExecutor executor;

    private static final ThreadLocal<OrderSubmitVO> threadOrderSubmit = new ThreadLocal<>();

    private OrderGoodsDao orderGoodsDao;

    private OrderDeliveryDao orderDeliveryDao;

    private OrderLogsDao orderLogsDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public OrderConfirmVO confirm(String skuId, Integer number) {
        List<OrderItemVO> items = getOrderItemFromCart(skuId, number);
        if (CollectionUtil.isEmpty(items)) {
            log.info("订单商品列表为空，无法创建订单确认信息");
            return new OrderConfirmVO();
        }

        // feign调用商品接口，获取商品信息
        Map<Long, SkuInfoVO> skuMap = new HashMap<>(items.size());
        List<String> skuIds = items.stream().map(item -> item.getSkuId().toString()).collect(Collectors.toList());
        List<SkuInfoVO> skuInfos = productFeignService.infos(skuIds).getData();
        if (!CollectionUtil.isEmpty(skuInfos)) {
            skuMap = skuInfos.stream().collect(Collectors.toMap(SkuInfoVO::getSkuId, Function.identity()));
        }

        for (OrderItemVO item : items) {
            SkuInfoVO info = skuMap.get(item.getSkuId());
            if (info != null) {
                item.setPrice(info.getSkuPrice());
                item.setSkuImg(info.getSkuPic());
                item.setSkuName(info.getSkuName());
            }
        }

        OrderConfirmVO confirm = new OrderConfirmVO();
        confirm.setItems(items);
        return confirm;
    }

    @Override
    @GlobalTransactional
    public OrderSubmitResultVO submit(OrderSubmitVO submit) throws ExecutionException, InterruptedException {
        log.info("开始创建订单：{}", submit);
        threadOrderSubmit.set(submit);
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        OrderVO orderVO = new OrderVO();
        CompletableFuture<Void> orderFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(attributes);
            threadOrderSubmit.set(submit);
            OrderEntity order = createOrder();
            orderVO.setOrderEntity(order);
        }, executor);

        CompletableFuture<Void> orderGoodsFuture = CompletableFuture.runAsync(() -> {
            // 生成订单商品信息
            RequestContextHolder.setRequestAttributes(attributes);
            threadOrderSubmit.set(submit);
            List<OrderGoodsEntity> orderGoods = createOrderGoods();
            orderVO.setOrderGoods(orderGoods);
        }, executor);

        CompletableFuture<Void> orderDeliveryFuture = CompletableFuture.runAsync(() -> {
            threadOrderSubmit.set(submit);
            RequestContextHolder.setRequestAttributes(attributes);
            // 生成订单地址信息
            OrderDeliveryEntity orderDelivery = createOrderDelivery();
            if (orderDelivery == null) {
                throw new BizException("提交订单失败，无法获取用户地址信息");
            }
            orderVO.setOrderDeliveryEntity(orderDelivery);
        }, executor);

        CompletableFuture<Void> future = CompletableFuture.allOf(orderFuture, orderGoodsFuture, orderDeliveryFuture);
        future.get();

        computePrice(orderVO.getOrderEntity(), orderVO.getOrderGoods());

        // 扣减库存
        lockStock(orderVO.getOrderGoods());

        // 保存订单
        this.baseMapper.insert(orderVO.getOrderEntity());
        Long orderId = orderVO.getOrderEntity().getId();

        // 保存订单商品
        for (OrderGoodsEntity orderGood : orderVO.getOrderGoods()) {
            orderGood.setOrderId(orderId);
            orderGoodsDao.insert(orderGood);
        }

        // 保存订单发货信息
        orderVO.getOrderDeliveryEntity().setOrderId(orderId);
        orderDeliveryDao.insert(orderVO.getOrderDeliveryEntity());

        // 保存订单日志
        // 清空购物车
        if (ObjectUtil.isNull(submit.getSkuId())) {
            cartService.cleanSelected();
        }

        OrderSubmitResultVO result = new OrderSubmitResultVO();
        result.setId(orderId);
        result.setOrderSn(orderVO.getOrderEntity().getOrderSn());
        return result;
    }

    private void lockStock(List<OrderGoodsEntity> orderGoods) {
        List<SkuStockVO> items = orderGoods.stream().map(good -> {
            SkuStockVO itemVO = new SkuStockVO();
            itemVO.setSkuId(good.getSkuId());
            itemVO.setNumber(good.getSkuQuantity());
            return itemVO;
        }).collect(Collectors.toList());
        WareSkuStockVO wareSkuStock = new WareSkuStockVO();
        wareSkuStock.setItems(items);
        Result result = productFeignService.lockStock(wareSkuStock);
        if (result == null || !StrUtil.equals(result.getCode(), ResultCode.SUCCESS.getCode())) {
            log.error("锁定库存异常，商品列表={}", items);
            throw new BizException("下单失败，锁定库存错误");
        }
    }


    /**
     * 计算订单商品价格
     *
     * @param order
     * @param orderGoods
     * @return
     */
    private Long computePrice(OrderEntity order, List<OrderGoodsEntity> orderGoods) {
        log.info("计算订单价格：order:{},orderGoods:{}", order, orderGoods);
        if (order == null || CollectionUtil.isEmpty(orderGoods)) {
            throw new BizException("订单或订单商品列表为空，订单创建失败");
        }
        Long totalAmount = orderGoods.stream().mapToLong(OrderGoodsEntity::getSkuTotalPrice).sum();
        int totalQuantity = orderGoods.stream().mapToInt(OrderGoodsEntity::getSkuQuantity).sum();
        Long payAmount = totalAmount;
        if (order.getCouponAmount() != null) {
            payAmount -= order.getCouponAmount();
        }
        if (order.getFreightAmount() != null) {
            payAmount -= order.getFreightAmount();
        }
        order.setTotalAmount(totalAmount);
        order.setTotalQuantity(totalQuantity);
        order.setPayAmount(payAmount);

        OrderSubmitVO submit = threadOrderSubmit.get();
        if (!StrUtil.equals(submit.getPayAmount().toString(), payAmount.toString())) {
            throw new BizException("订单价格变化，请重新提交");
        }
        return payAmount;

    }

    /**
     * 创建订单商品集合
     *
     * @return
     */
    private List<OrderGoodsEntity> createOrderGoods() {
        OrderSubmitVO submit = threadOrderSubmit.get();
        log.info("创建订单商品实体类，submit:{}", submit);
        List<OrderGoodsEntity> orderGoods = null;
        if (ObjectUtil.isNull(submit.getSkuId())) {
            CartVo detail = cartService.detail();
            log.info("从购物车中获取已选择商品信息：{}", detail);
            orderGoods = detail.getItems().stream().map(item -> {
                OrderGoodsEntity good = new OrderGoodsEntity();
                good.setSkuId(item.getSkuId());
                good.setSkuQuantity(item.getNumber());
                return good;
            }).collect(Collectors.toList());
        } else {
            log.info("会员选择商品直接下单,商品id:{}", submit.getSkuId());
            orderGoods = new ArrayList<>();
            OrderGoodsEntity good = new OrderGoodsEntity();
            good.setSkuId(Long.valueOf(submit.getSkuId()));
            good.setSkuQuantity(submit.getSkuNumber());
            orderGoods.add(good);
        }

        List<String> skuIds = orderGoods.stream().map(vo -> vo.getSkuId().toString()).collect(Collectors.toList());
        Result<List<SkuInfoVO>> response = productFeignService.infos(skuIds);
        List<SkuInfoVO> skuInfos = response.getData();
        if (skuInfos == null) {
            return null;
        }
        Map<Long, SkuInfoVO> skuMap = skuInfos.stream().collect(Collectors.toMap(SkuInfoVO::getSkuId, Function.identity(), (o1, o2) -> o2));
        for (OrderGoodsEntity good : orderGoods) {
            SkuInfoVO skuInfo = skuMap.get(good.getSkuId());
            if (skuInfo == null) {
                throw new BizException("订单商品库存为空");
            }
            BeanMapperUtils.copy(skuInfo, good);
            Long goodTotalPrice = good.getSkuPrice() * good.getSkuQuantity();
            good.setSkuTotalPrice(goodTotalPrice);
        }
        return orderGoods;

    }

    /**
     * 生成订单数据
     *
     * @return
     */
    private OrderEntity createOrder() {
        OrderSubmitVO submit = threadOrderSubmit.get();
        log.info("创建订单实体类，submit:{}", submit);
        OrderEntity order = new OrderEntity();
        order.setOrderSn(IdWorker.getTimeId());
        order.setRemark(submit.getRemark());
        order.setStatus(OrderStatusEnum.NEED_PAY.code);
        order.setSourceType(OrderTypeEnum.APP.code);
        order.setMemberId(WebUtils.getUserId());
        return order;

    }

    /**
     * 获取订单地址信息
     *
     * @return
     */
    private OrderDeliveryEntity createOrderDelivery() {
        String addressId = threadOrderSubmit.get().getAddressId();
        log.info("获取订单地址信息，addressId：{}", addressId);
        try {
            Result<UmsAddressDTO> response = memberFeignService.getAddressById(addressId);
            UmsAddressDTO addressInfo = response.getData();
            if (addressInfo != null) {
                OrderDeliveryEntity delivery = new OrderDeliveryEntity();
                delivery.setReceiverProvince(addressInfo.getProvince());
                delivery.setReceiverCity(addressInfo.getCity());
                delivery.setReceiverRegion(addressInfo.getArea());
                delivery.setReceiverDetailAddress(addressInfo.getAddress());
                delivery.setReceiverName(addressInfo.getName());
                delivery.setReceiverPostCode(addressInfo.getZipCode());
                delivery.setReceiverPhone(addressInfo.getMobile());
                return delivery;
            }
        } catch (Exception e) {
            log.error("获取订单地址信息失败，addressId：{}", addressId, e);
        }
        return null;
    }

    /**
     * 从购物车中获取订单商品列表
     *
     * @return
     */
    private List<OrderItemVO> getOrderItemFromCart(String skuId, Integer number) {
        if (!StrUtil.isEmpty(skuId)) {
            log.info("请求携带商品id：{}，数量：{}，订单类型为用户直接购买", skuId, number);
            OrderItemVO itemVO = OrderItemVO.builder().skuId(Long.parseLong(skuId)).number(number).build();
            return Arrays.asList(itemVO);

        }
        CartVo cartVo = cartService.detail();
        List<OrderItemVO> items = cartVo.getItems().stream().filter(CartItemVo::isChecked).map(cart -> OrderItemVO.builder().skuId(cart.getSkuId()).number(cart.getNumber()).build()).collect(Collectors.toList());
        return items;
    }

}