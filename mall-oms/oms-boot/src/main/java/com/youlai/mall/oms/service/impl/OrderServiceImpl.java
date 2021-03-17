package com.youlai.mall.oms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
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
import com.youlai.mall.oms.constant.OmsConstants;
import com.youlai.mall.oms.enums.OrderStatusEnum;
import com.youlai.mall.oms.enums.OrderTypeEnum;
import com.youlai.mall.oms.mapper.OrderMapper;
import com.youlai.mall.oms.pojo.bo.app.OrderBO;
import com.youlai.mall.oms.pojo.domain.OmsOrder;
import com.youlai.mall.oms.pojo.domain.OmsOrderDelivery;
import com.youlai.mall.oms.pojo.domain.OmsOrderItem;
import com.youlai.mall.oms.pojo.dto.OrderConfirmDTO;
import com.youlai.mall.oms.pojo.dto.OrderSubmitDTO;
import com.youlai.mall.oms.pojo.vo.*;
import com.youlai.mall.oms.service.*;
import com.youlai.mall.pms.api.app.PmsSkuFeignService;
import com.youlai.mall.pms.pojo.domain.PmsSku;
import com.youlai.mall.pms.pojo.dto.InventoryDTO;
import com.youlai.mall.pms.pojo.dto.SkuDTO;
import com.youlai.mall.ums.api.UmsAddressFeignService;
import com.youlai.mall.ums.api.UmsMemberFeignService;
import com.youlai.mall.ums.pojo.domain.UmsAddress;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OmsOrder> implements IOrderService {

    private static final ThreadLocal<OrderSubmitDTO> threadLocal = new ThreadLocal<>();

    private ICartService cartService;

    private PmsSkuFeignService skuFeignService;

    private UmsMemberFeignService memberFeignService;

    private UmsAddressFeignService addressFeignService;

    private AsyncTaskExecutor executor;

    private IOrderItemService orderItemService;

    private IOrderDeliveryService orderDeliveryService;

    private IOrderLogService orderLogService;

    private RabbitTemplate rabbitTemplate;

    private RedisTemplate redisTemplate;

    private ThreadPoolExecutor threadPoolExecutor;


    @Override
    public OrderConfirmVO confirm(OrderConfirmDTO orderConfirmDTO) {
        OrderConfirmVO orderConfirmVO = new OrderConfirmVO();
        // 获取购买商品信息
        CompletableFuture<Void> orderItemsCompletableFuture = CompletableFuture.runAsync(() -> {
            List<OrderItemVO> orderItems = new ArrayList<>();
            if (orderConfirmDTO.getSkuId() != null) { // 直接购买商品结算
                OrderItemVO orderItemVO = OrderItemVO.builder()
                        .skuId(orderConfirmDTO.getSkuId())
                        .count(orderConfirmDTO.getCount())
                        .build();
                PmsSku sku = skuFeignService.getSkuById(orderConfirmDTO.getSkuId()).getData();
                orderItemVO.setPrice(sku.getPrice());
                orderItemVO.setSkuPic(sku.getPic());
                orderItemVO.setTitle(sku.getTitle());
                orderItems.add(orderItemVO);
            } else { // 购物车中商品结算
                List<CartVO.CartItem> cartItems = cartService.getCartItems();
                List<OrderItemVO> items = cartItems.stream()
                        .filter(CartVO.CartItem::isChecked)
                        .map(cartItem -> OrderItemVO.builder()
                                .skuId(cartItem.getSkuId())
                                .count(cartItem.getCount())
                                .price(cartItem.getPrice())
                                .title(cartItem.getTitle())
                                .skuPic(cartItem.getPic())
                                .build())
                        .collect(Collectors.toList());
                orderItems.addAll(items);
            }
            orderConfirmVO.setOrderItems(orderItems);
        }, threadPoolExecutor);

        // 获取会员地址列表
        CompletableFuture<Void> addressesCompletableFuture = CompletableFuture.runAsync(() -> {
            List<UmsAddress> addresses = addressFeignService.list().getData();
            orderConfirmVO.setAddresses(addresses);
        }, threadPoolExecutor);


        // 生成唯一标识，防止订单重复提交
        CompletableFuture<Void> orderTokenCompletableFuture = CompletableFuture.runAsync(() -> {
            String orderToken = IdUtil.randomUUID();
            orderConfirmVO.setOrderToken(orderToken);
            redisTemplate.opsForValue().set(OmsConstants.ORDER_TOKEN_PREFIX + orderToken, orderToken);
        }, threadPoolExecutor);

        CompletableFuture.allOf(orderItemsCompletableFuture, addressesCompletableFuture, orderTokenCompletableFuture);
        return orderConfirmVO;
    }

    @Override
    @GlobalTransactional
    public OrderSubmitVO submit(OrderSubmitDTO submitDTO) {

        submitDTO.




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
                OrderSubmitDTO submitInfo = threadLocal.get();
                log.info("订单提交信息:{}", submitInfo);
                OmsOrder order = new OmsOrder();
                order.setOrderSn(IdWorker.getTimeId())
                        .setRemark(submitInfo.getRemark())
                        .setStatus(OrderStatusEnum.PENDING_PAYMENT.getCode())
                        .setSourceType(OrderTypeEnum.APP.getCode())
                        .setUserId(RequestUtils.getUserId());
                log.debug("完善后的订单信息：{}", order.toString());
                orderBO.setOrder(order);

            }, executor);
        }

        // 创建订单商品任务
        {
            orderItemFuture = CompletableFuture.runAsync(() -> {
                RequestContextHolder.setRequestAttributes(attributes);
                threadLocal.set(submitInfoDTO);
                OrderSubmitDTO submitInfo = threadLocal.get();
                List<OmsOrderItem> orderItems;
                if (submitInfoDTO.getSkuId() != null) { // 直接下单
                    orderItems = new ArrayList<>();
                    orderItems.add(OmsOrderItem.builder().skuId(submitInfo.getSkuId()).skuQuantity(submitInfo.getSkuNum()).build());
                } else { // 购物车下单
                    CartVO cart = cartService.getCart();
                    orderItems = cart.getItems().stream().map(cartItem -> OmsOrderItem.builder().skuId(cartItem.getSkuId())
                            .skuQuantity(cartItem.getCount())
                            .build()
                    ).collect(Collectors.toList());
                }

                List<Long> skuIds = orderItems.stream().map(item -> item.getSkuId())
                        .collect(Collectors.toList());

                List<SkuDTO> skuList = inventoryFeignService.listBySkuIds(skuIds).getData();
                if (CollectionUtil.isEmpty(skuList)) {
                    throw new BizException("订单商品库存为空");
                }
                for (OmsOrderItem orderItem : orderItems) {
                    skuList.stream().filter(sku -> sku.getId().equals(orderItem.getSkuId())).findFirst()
                            .ifPresent(skuItem -> {
                                BeanUtil.copyProperties(skuItem, orderItem);
                                orderItem.setSkuPrice(skuItem.getPrice());
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
                UmsAddressDTO userAddress = memberFeignService.getAddressById(addressId).getData();
                if (userAddress == null) {
                    throw new BizException("会员地址不存在");
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


            OrderSubmitDTO orderSubmitInfo = threadLocal.get();
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
                    .count(orderItem.getSkuQuantity())
                    .build())
                    .collect(Collectors.toList());

            Result result = inventoryFeignService.lockStock(items);
            if (!StrUtil.equals(result.getCode(), ResultCode.SUCCESS.getCode())) {
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

        // 将订单放入延时队列，超时未支付系统自动关单
        rabbitTemplate.convertAndSend("order-exchange", "order:create", orderId);

        OrderSubmitVO result = new OrderSubmitVO();
        result.setId(orderId);
        result.setOrderSn(order.getOrderSn());
        return result;
    }


    @Override
    public boolean closeOrder(Long orderId) {
        OmsOrder order = this.getOne(new LambdaQueryWrapper<OmsOrder>().eq(OmsOrder::getId, orderId));
        if (!OrderStatusEnum.PENDING_PAYMENT.getCode().equals(order.getStatus())) {
            return false;
        }
        order.setStatus(OrderStatusEnum.AUTO_CANCEL.getCode());
        this.updateById(order);
        orderLogService.addOrderLogs(order.getId(), order.getStatus(), "系统操作", OrderStatusEnum.AUTO_CANCEL.getText());
        return true;
    }

    @Override
    public boolean cancelOrder(Long id) {
        log.info("会员取消订单，orderId={}", id);
        OmsOrder order = getByOrderId(id);
        if (!OrderStatusEnum.PENDING_PAYMENT.getCode().equals(order.getStatus())) {
            throw new BizException("订单状态非待支付，取消失败");
        }
        order.setStatus(OrderStatusEnum.USER_CANCEL.getCode());
        this.updateById(order);
        orderLogService.addOrderLogs(order.getId(), order.getStatus(), OrderStatusEnum.USER_CANCEL.getText());
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteOrder(Long id) {
        // 查询订单，校验订单状态
        OmsOrder order = this.getByOrderId(id);
        if (!OrderStatusEnum.AUTO_CANCEL.getCode().equals(order.getStatus()) &&
                !OrderStatusEnum.USER_CANCEL.getCode().equals(order.getStatus())) {
            throw new BizException("订单状态不允许删除");
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

        List<Long> orderIds = orderList.stream().map(order -> order.getId()).collect(Collectors.toList());
        Map<Long, List<OmsOrderItem>> orderItemsMap = orderItemService.getByOrderIds(orderIds);

        List<OrderListVO> result = orderList.stream().map(order -> {
            OrderListVO orderListVO = BeanMapperUtils.map(order, OrderListVO.class);
            orderListVO.setStatusDesc(OrderStatusEnum.getValue(orderListVO.getStatus()).getText());
            List<OmsOrderItem> orderItems = orderItemsMap.get(orderListVO.getId());
            if (CollectionUtil.isNotEmpty(orderItems)) {
                List<OrderListVO.OrderItemBean> orderItemBeans = orderItems.stream()
                        .map(orderItem -> BeanMapperUtils.map(orderItem, OrderListVO.OrderItemBean.class))
                        .collect(Collectors.toList());
                orderListVO.setOrderItemLIst(orderItemBeans);
            }
            return orderListVO;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public OmsOrder getByOrderId(Long id) {
        Long userId = RequestUtils.getUserId();
        OmsOrder order = this.getOne(new LambdaQueryWrapper<OmsOrder>()
                .eq(OmsOrder::getId, id)
                .eq(OmsOrder::getUserId, userId));
        if (order == null) {
            throw new BizException("订单不存在，订单ID非法");
        }
        return order;
    }


    /**
     * 获取订单商品 1. 直接购买  2. 购物车结算
     */
    private List<OrderItemVO> getOrderItems(Long skuId, Integer count) {
        if (skuId != null) { // 直接购买
            OrderItemVO itemVO = OrderItemVO.builder()
                    .skuId(skuId)
                    .count(count)
                    .build();
            return Arrays.asList(itemVO);
        }
        // 购物车结算，从购物车获取商品
        CartVO cart = cartService.getCart();
        List<OrderItemVO> items = cart.getItems().stream()
                .filter(CartItemVO::isChecked)
                .map(item -> OrderItemVO.builder().skuId(item.getSkuId()).count(item.getCount()).build())
                .collect(Collectors.toList());
        return items;
    }

}
