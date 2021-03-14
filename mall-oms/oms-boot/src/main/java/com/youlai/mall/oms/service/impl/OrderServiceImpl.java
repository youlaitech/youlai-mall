package com.youlai.mall.oms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import com.youlai.common.web.exception.BizException;
import com.youlai.common.web.util.BeanMapperUtils;
import com.youlai.common.web.util.RequestUtils;
import com.youlai.mall.oms.config.rabbitmq.OmsRabbitConstants;
import com.youlai.mall.oms.dao.OrderDao;
import com.youlai.mall.oms.enums.OrderStatusEnum;
import com.youlai.mall.oms.enums.OrderTypeEnum;
import com.youlai.mall.oms.pojo.bo.app.OrderBO;
import com.youlai.mall.oms.pojo.domain.OmsOrderDelivery;
import com.youlai.mall.oms.pojo.domain.OmsOrder;
import com.youlai.mall.oms.pojo.domain.OmsOrderItem;
import com.youlai.mall.oms.pojo.dto.OrderSubmitInfoDTO;
import com.youlai.mall.oms.pojo.vo.*;
import com.youlai.mall.oms.service.*;
import com.youlai.mall.pms.api.app.InventoryFeignService;
import com.youlai.mall.pms.pojo.dto.SkuDTO;
import com.youlai.mall.pms.pojo.dto.InventoryDTO;
import com.youlai.mall.ums.api.app.MemberFeignService;
import com.youlai.mall.ums.pojo.dto.UmsAddressDTO;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderDao, OmsOrder> implements IOrderService {

    private static final ThreadLocal<OrderSubmitInfoDTO> threadLocal = new ThreadLocal<>();

    private ICartService cartService;

    private InventoryFeignService inventoryFeignService;

    private MemberFeignService memberFeignService;

    private AsyncTaskExecutor executor;

    private IOrderItemService orderItemService;

    private IOrderDeliveryService orderDeliveryService;

    private IOrderLogService orderLogService;

    private RabbitTemplate rabbitTemplate;


    @Override
    public OrderConfirmVO confirm(Long skuId, Integer num) {
        List<OrderItemVO> orderItems = getOrderItem(skuId, num);
        if (CollectionUtil.isEmpty(orderItems)) { // 订单中无商品，直接返回
            return new OrderConfirmVO();
        }

        // 远程获取商品信息填充订单商品属性
        List<Long> skuIds = orderItems.stream().map(item -> item.getSkuId()).collect(Collectors.toList());
        List<SkuDTO> skus = inventoryFeignService.listBySkuIds(skuIds).getData();
        for (OrderItemVO orderItem : orderItems) {
            skus.stream().filter(sku -> sku.getId().equals(orderItem.getSkuId())).findFirst()
                    .ifPresent(skuItem -> {
                        orderItem.setPrice(skuItem.getPrice());
                        orderItem.setSkuImg(skuItem.getPic());
                        orderItem.setSkuName(skuItem.getName());
                    });
        }

        OrderConfirmVO confirm = new OrderConfirmVO();
        confirm.setItems(orderItems);
        return confirm;
    }

    @Override
    @GlobalTransactional
    @SneakyThrows
    public OrderSubmitResultVO submit(OrderSubmitInfoDTO submitInfoDTO) {
        log.info("开始创建订单：{}", submitInfoDTO);
        threadLocal.set(submitInfoDTO);
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();

        OrderBO orderBO = new OrderBO();
        CompletableFuture<Void> orderFuture;
        CompletableFuture<Void> orderItemFuture;
        CompletableFuture<Void> orderDeliveryFuture;

        // 创建订单任务
        {
            orderFuture = CompletableFuture.runAsync(() -> {
                RequestContextHolder.setRequestAttributes(attributes);
                threadLocal.set(submitInfoDTO);

                OrderSubmitInfoDTO submitInfo = threadLocal.get();
                log.info("创建订单实体类，submit:{}", submitInfo);
                OmsOrder order = new OmsOrder();
                order.setOrderSn(IdWorker.getTimeId())
                        .setRemark(submitInfo.getRemark())
                        .setStatus(OrderStatusEnum.NEED_PAY.getCode())
                        .setSourceType(OrderTypeEnum.APP.getCode())
                        .setMemberId(RequestUtils.getUserId());
                orderBO.setOrder(order);

            }, executor);
        }

        // 创建订单商品任务
        {
            orderItemFuture = CompletableFuture.runAsync(() -> {
                RequestContextHolder.setRequestAttributes(attributes);
                threadLocal.set(submitInfoDTO);

                OrderSubmitInfoDTO submitInfo = threadLocal.get();
                log.info("创建订单商品，submitInfo:{}", submitInfo);

                List<OmsOrderItem> orderItems;
                if (submitInfoDTO.getSkuId() != null) { // 直接下单
                    orderItems = new ArrayList<>();
                    orderItems.add(OmsOrderItem.builder().skuId(submitInfo.getSkuId()).skuQuantity(submitInfo.getSkuNumber()).build());
                } else { // 购物车下单
                    CartVO cart = cartService.getCart();
                    orderItems = cart.getItems().stream().map(cartItem -> OmsOrderItem.builder().skuId(cartItem.getSkuId())
                            .skuQuantity(cartItem.getNum())
                            .build()
                    ).collect(Collectors.toList());
                }

                List<Long> skuIds = orderItems.stream().map(item -> item.getSkuId())
                        .collect(Collectors.toList());

                List<SkuDTO> skus = inventoryFeignService.listBySkuIds(skuIds).getData();
                if (CollectionUtil.isEmpty(skus)) {
                    throw new BizException("订单商品库存为空");
                }
                for (OmsOrderItem orderItem : orderItems) {
                    skus.stream().filter(sku -> sku.getId().equals(orderItem.getSkuId())).findFirst()
                            .ifPresent(skuItem -> {
                                BeanUtil.copyProperties(skuItem, orderItem);
                                orderItem.setSkuTotalPrice(orderItem.getSkuPrice() * orderItem.getSkuQuantity());
                            });
                }
                orderBO.setOrderItems(orderItems);
            }, executor);
        }

        // 创建发货信息任务
        {
            orderDeliveryFuture = CompletableFuture.runAsync(() -> {
                RequestContextHolder.setRequestAttributes(attributes);
                threadLocal.set(submitInfoDTO);

                String addressId = threadLocal.get().getAddressId();
                log.info("获取订单地址信息，addressId：{}", addressId);
                UmsAddressDTO userAddress = memberFeignService.getAddressById(addressId).getData();
                if (userAddress == null) {
                    throw new BizException("提交订单失败，无法获取用户地址信息");
                }
                OmsOrderDelivery orderDelivery = OmsOrderDelivery.builder()
                        .receiverName(userAddress.getName())
                        .receiverPhone(userAddress.getMobile())
                        .receiverPostCode(userAddress.getZipCode())
                        .receiverProvince(userAddress.getProvince())
                        .receiverCity(userAddress.getCity())
                        .receiverRegion(userAddress.getArea())
                        .receiverDetailAddress(userAddress.getAddress())
                        .build();

                orderBO.setOrderDelivery(orderDelivery);
            }, executor);
        }

        CompletableFuture<Void> future = CompletableFuture.allOf(orderFuture, orderItemFuture, orderDeliveryFuture);
        future.get();

        // 订单验价
        {
            OmsOrder order = orderBO.getOrder();
            List<OmsOrderItem> orderItems = orderBO.getOrderItems();

            log.info("计算订单价格：order:{}，orderItems:{}", order, orderItems);

            if (order == null || CollectionUtil.isEmpty(orderItems)) {
                throw new BizException("订单或订单商品列表为空，订单创建失败");
            }

            Long totalAmount = orderItems.stream().mapToLong(OmsOrderItem::getSkuTotalPrice).sum();
            int totalQuantity = orderItems.stream().mapToInt(OmsOrderItem::getSkuQuantity).sum();
            Long payAmount = totalAmount;
            if (order.getCouponAmount() != null) {
                payAmount -= order.getCouponAmount();
            }
            if (order.getFreightAmount() != null) {
                payAmount -= order.getFreightAmount();
            }


            OrderSubmitInfoDTO orderSubmitInfo = threadLocal.get();
            int compare = Long.compare(orderSubmitInfo.getPayAmount().longValue(), payAmount.longValue());
            if (compare != 0) {
                throw new BizException("订单价格变化，请重新提交");
            }

            order.setTotalAmount(totalAmount);
            order.setTotalQuantity(totalQuantity);
            order.setPayAmount(payAmount);
        }

        // 锁定库存
        {
            List<OmsOrderItem> orderItems = orderBO.getOrderItems();
            List<InventoryDTO> items = orderItems.stream().map(orderItem -> InventoryDTO.builder()
                    .skuId(orderItem.getSkuId())
                    .num(orderItem.getSkuQuantity())
                    .build())
                    .collect(Collectors.toList());

            Result result = inventoryFeignService.lockInventory(items);
            if (result == null || !StrUtil.equals(result.getCode(), ResultCode.SUCCESS.getCode())) {
                throw new BizException("下单失败，锁定库存错误");
            }
        }

        // 保存订单
        OmsOrder order = orderBO.getOrder();
        this.save(order);
        Long orderId = order.getId();

        // 保存订单商品
        List<OmsOrderItem> orderItems = orderBO.getOrderItems();
        orderItems.forEach(item -> item.setOrderId(orderId));
        orderItemService.saveBatch(orderItems);

        // 保存发货信息
        OmsOrderDelivery orderDelivery = orderBO.getOrderDelivery();
        orderDelivery.setOrderId(orderId);
        orderDeliveryService.save(orderDelivery);

        // 删除购物车中已购买的商品
        if (ObjectUtil.isNull(submitInfoDTO.getSkuId())) {
            cartService.deleteSelectedItem();
        }

        // 将订单放入定时队列中，超时未支付系统自动关单，释放库存
        rabbitTemplate.convertAndSend(OmsRabbitConstants.ORDER_EVENT_EXCHANGE,
                OmsRabbitConstants.ORDER_CREATE_ORDER_KEY,
                orderBO.getOrder().getOrderSn()
        );

        // 保存日志
        orderLogService.addOrderLogs(orderId,
                orderBO.getOrder().getStatus(),
                RequestUtils.getUserId().toString(),
                "创建订单"
        );

        OrderSubmitResultVO result = new OrderSubmitResultVO();
        result.setId(orderId);
        result.setOrderSn(order.getOrderSn());
        return result;
    }


    @Override
    public boolean closeOrderBySystem(String orderSn) {
        log.info("订单超时未支付，系统自动关闭，orderSn={}", orderSn);
        OmsOrder order = this.getOne(new LambdaQueryWrapper<OmsOrder>().eq(OmsOrder::getOrderSn, orderSn));
        if (!order.getStatus().equals(OrderStatusEnum.NEED_PAY.getCode())) {
            log.info("订单状态异常，系统无法自动关闭，orderSn={}，orderStatus={}", orderSn, order.getStatus());
            return false;
        }
        order.setStatus(OrderStatusEnum.SYS_CANCEL.getCode());
        baseMapper.updateById(order);
        // 添加订单操作日志
        orderLogService.addOrderLogs(order.getId(), order.getStatus(),
                "系统操作", OrderStatusEnum.SYS_CANCEL.getText());
        return true;

    }

    @Override
    public boolean cancelOrder(Long id) {
        log.info("会员取消订单，orderId={}", id);
        OmsOrder order = getByOrderId(id);
        if (!order.getStatus().equals(OrderStatusEnum.NEED_PAY.getCode())) {
            log.info("订单状态异常，会员无法取消，orderId={}，orderStatus={}", id, order.getStatus());
            return false;
        }
        order.setStatus(OrderStatusEnum.USER_CANCEL.getCode());
        baseMapper.updateById(order);
        // 添加订单操作日志
        orderLogService.addOrderLogs(order.getId(), order.getStatus(), OrderStatusEnum.USER_CANCEL.getText());
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteOrder(Long id) {
        // 查询订单，校验订单状态
        OmsOrder order = this.getByOrderId(id);
        if (!order.getStatus().equals(OrderStatusEnum.SYS_CANCEL.getCode()) &&
                order.getStatus().equals(OrderStatusEnum.USER_CANCEL.getCode())) {
            throw new BizException(StrUtil.format("订单无法删除，订单状态【{}】", Objects.requireNonNull(OrderStatusEnum.getValue(order.getStatus())).getText()));
        }
        this.removeById(id);
        orderLogService.addOrderLogs(order.getId(), order.getStatus(), "会员删除订单");
        return true;
    }

    @Override
    public List<OrderListVO> list(Integer status) {
        log.info("订单列表查询，status={}", status);
        QueryWrapper<OmsOrder> orderQuery = new QueryWrapper<>();
        if (status != 0) {
            orderQuery.eq("status", status);
        }
        orderQuery.orderByDesc("id");
        List<OmsOrder> orderList = this.list(orderQuery);
        if (orderList == null || orderList.size() <= 0) {
            log.info("订单列表查询结果为空，status={}", status);
            return null;
        }

        List<Long> orderIds = orderList.stream().map(orderEntity -> orderEntity.getId()).collect(Collectors.toList());
        Map<Long, List<OmsOrderItem>> orderGoodsMap = orderItemService.getByOrderIds(orderIds);
        List<OrderListVO> result = orderList.stream().map(orderEntity -> {
            OrderListVO orderListVO = BeanMapperUtils.map(orderEntity, OrderListVO.class);
            orderListVO.setStatusDesc(OrderStatusEnum.getValue(orderListVO.getStatus()).getText());
            List<OmsOrderItem> orderGoodsEntities = orderGoodsMap.get(orderListVO.getId());
            if (orderGoodsEntities != null && orderGoodsEntities.size() > 0) {
                List<OrderListVO.GoodsListBean> goodsListBeans = orderGoodsEntities.stream().map(orderGoodsEntity -> BeanMapperUtils.map(orderGoodsEntity, OrderListVO.GoodsListBean.class)).collect(Collectors.toList());
                orderListVO.setGoodsList(goodsListBeans);
            }
            return orderListVO;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public OmsOrder getByOrderId(Long id) {
        Long userId = RequestUtils.getUserId();
        QueryWrapper<OmsOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id", userId).eq("id", id);
        OmsOrder order = this.getOne(new LambdaQueryWrapper<OmsOrder>()
                .eq(OmsOrder::getId, id)
                .eq(OmsOrder::getMemberId, userId));
        if (order == null) {
            throw new BizException("订单不存在，订单ID非法");
        }
        return order;
    }


    /**
     * 获取订单商品 1. 直接购买  2. 购物车结算
     *
     * @return
     */
    private List<OrderItemVO> getOrderItem(Long skuId, Integer num) {
        if (skuId != null) { // 直接购买
            OrderItemVO itemVO = OrderItemVO.builder()
                    .skuId(skuId)
                    .number(num)
                    .build();
            return Arrays.asList(itemVO);
        }
        // 购物车结算，从购物车获取商品
        CartVO cart = cartService.getCart();
        List<OrderItemVO> items = cart.getItems().stream()
                .filter(CartItemVO::isChecked)
                .map(item -> OrderItemVO.builder().skuId(item.getSkuId()).number(item.getNum()).build())
                .collect(Collectors.toList());
        return items;
    }

}
