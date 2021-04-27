package com.youlai.mall.oms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.enums.BusinessTypeEnum;
import com.youlai.common.redis.component.BusinessNoGenerator;
import com.youlai.common.result.Result;
import com.youlai.common.web.exception.BizException;
import com.youlai.common.web.util.RequestUtils;
import com.youlai.mall.oms.enums.OrderStatusEnum;
import com.youlai.mall.oms.enums.OrderTypeEnum;
import com.youlai.mall.oms.enums.PayTypeEnum;
import com.youlai.mall.oms.mapper.OrderMapper;
import com.youlai.mall.oms.pojo.domain.OmsOrder;
import com.youlai.mall.oms.pojo.domain.OmsOrderItem;
import com.youlai.mall.oms.pojo.dto.OrderConfirmDTO;
import com.youlai.mall.oms.pojo.dto.OrderItemDTO;
import com.youlai.mall.oms.pojo.dto.OrderSubmitDTO;
import com.youlai.mall.oms.pojo.vo.CartVO;
import com.youlai.mall.oms.pojo.vo.OrderConfirmVO;
import com.youlai.mall.oms.pojo.vo.OrderSubmitVO;
import com.youlai.mall.oms.service.ICartService;
import com.youlai.mall.oms.service.IOrderItemService;
import com.youlai.mall.oms.service.IOrderService;
import com.youlai.mall.pms.api.app.PmsSkuFeignService;
import com.youlai.mall.pms.pojo.dto.SkuDTO;
import com.youlai.mall.pms.pojo.dto.SkuLockDTO;
import com.youlai.mall.ums.api.MemberAddressFeignClient;
import com.youlai.mall.ums.api.MemberFeignClient;
import com.youlai.mall.ums.pojo.domain.UmsAddress;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import static com.youlai.mall.oms.constant.OmsConstants.*;

@AllArgsConstructor
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OmsOrder> implements IOrderService {

    private ICartService cartService;
    private PmsSkuFeignService skuFeignService;
    private MemberAddressFeignClient addressFeignService;
    private IOrderItemService orderItemService;
    private RabbitTemplate rabbitTemplate;
    private StringRedisTemplate redisTemplate;
    private ThreadPoolExecutor threadPoolExecutor;
    private MemberFeignClient memberFeignClient;

    private BusinessNoGenerator businessNoGenerator;

    /**
     * 订单确认
     */
    @Override
    public OrderConfirmVO confirm(OrderConfirmDTO orderConfirmDTO) {
        log.info("=======================订单确认=======================\n订单确认信息：{}", orderConfirmDTO);
        OrderConfirmVO orderConfirmVO = new OrderConfirmVO();
        Long memberId = RequestUtils.getUserId();
        // 获取购买商品信息
        CompletableFuture<Void> orderItemsCompletableFuture = CompletableFuture.runAsync(() -> {
            List<OrderItemDTO> orderItems = new ArrayList<>();
            if (orderConfirmDTO.getSkuId() != null) { // 直接购买商品结算
                OrderItemDTO orderItemDTO = OrderItemDTO.builder()
                        .skuId(orderConfirmDTO.getSkuId())
                        .count(orderConfirmDTO.getCount())
                        .build();
                SkuDTO sku = skuFeignService.getSkuById(orderConfirmDTO.getSkuId()).getData();
                orderItemDTO.setPrice(sku.getPrice());
                orderItemDTO.setPic(sku.getPic());
                orderItemDTO.setSkuName(sku.getName());
                orderItemDTO.setSkuCode(sku.getCode());
                orderItemDTO.setSpuName(sku.getSpuName());
                orderItems.add(orderItemDTO);
            } else { // 购物车中商品结算
                List<CartVO.CartItem> cartItems = cartService.getCartItems(memberId);
                List<OrderItemDTO> items = cartItems.stream()
                        .filter(CartVO.CartItem::getChecked)
                        .map(cartItem -> OrderItemDTO.builder()
                                .skuId(cartItem.getSkuId())
                                .count(cartItem.getCount())
                                .price(cartItem.getPrice())
                                .skuName(cartItem.getSkuName())
                                .skuCode(cartItem.getSkuCode())
                                .spuName(cartItem.getSpuName())
                                .pic(cartItem.getPic())
                                .build())
                        .collect(Collectors.toList());
                orderItems.addAll(items);
            }
            orderConfirmVO.setOrderItems(orderItems);
        }, threadPoolExecutor);

        // 获取会员地址列表
        CompletableFuture<Void> addressesCompletableFuture = CompletableFuture.runAsync(() -> {
            List<UmsAddress> addresses = addressFeignService.list(memberId).getData();
            orderConfirmVO.setAddresses(addresses);
        }, threadPoolExecutor);


        // 生成唯一标识，防止订单重复提交
        CompletableFuture<Void> orderTokenCompletableFuture = CompletableFuture.runAsync(() -> {
            String orderToken = businessNoGenerator.generate(BusinessTypeEnum.ORDER);
            orderConfirmVO.setOrderToken(orderToken);
            redisTemplate.opsForValue().set(ORDER_TOKEN_PREFIX + orderToken, orderToken);
        }, threadPoolExecutor);

        CompletableFuture.allOf(orderItemsCompletableFuture, addressesCompletableFuture, orderTokenCompletableFuture).join();
        log.info("订单确认响应：{}", orderConfirmVO.toString());
        return orderConfirmVO;
    }

    /**
     * 订单提交
     */
    @Override
    @GlobalTransactional
    public OrderSubmitVO submit(OrderSubmitDTO submitDTO) {
        log.info("=======================订单提交=======================\n订单提交信息：{}", submitDTO);
        // 订单重复提交校验
        String orderToken = submitDTO.getOrderToken();
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(RELEASE_LOCK_LUA_SCRIPT, Long.class);
        Long result = this.redisTemplate.execute(redisScript, Collections.singletonList(ORDER_TOKEN_PREFIX + orderToken), orderToken);

        if (!ObjectUtil.equals(result, RELEASE_LOCK_SUCCESS_RESULT)) {
            throw new BizException("订单不可重复提交");
        }

        List<OrderItemDTO> orderItems = submitDTO.getOrderItems();
        if (CollectionUtil.isEmpty(orderItems)) {
            throw new BizException("订单没有商品，请选择商品后提交");
        }

        // 订单验价
        Long currentTotalPrice = orderItems.stream().map(item -> {
            SkuDTO sku = skuFeignService.getSkuById(item.getSkuId()).getData();
            if (sku != null) {
                return sku.getPrice() * item.getCount();
            }
            return 0l;
        }).reduce(0l, Long::sum);

        if (currentTotalPrice.compareTo(submitDTO.getTotalPrice()) != 0) {
            throw new BizException("页面已过期，请重新刷新页面再提交");
        }

        // 校验库存是否足够和锁库存
        List<SkuLockDTO> skuLockList = orderItems.stream()
                .map(item -> SkuLockDTO.builder().skuId(item.getSkuId())
                        .count(item.getCount())
                        .orderToken(orderToken)
                        .build())
                .collect(Collectors.toList());

        Result lockResult = skuFeignService.lockStock(skuLockList);

        if (!Result.success().getCode().equals(lockResult.getCode())) {
            throw new BizException(Result.failed().getMsg());
        }

        // 创建订单(状态：待支付)
        OmsOrder order = new OmsOrder();
        order.setOrderSn(orderToken) // 把orderToken赋值给订单编号【!】
                .setStatus(OrderStatusEnum.PENDING_PAYMENT.getCode())
                .setSourceType(OrderTypeEnum.APP.getCode())
                .setMemberId(RequestUtils.getUserId())
                .setRemark(submitDTO.getRemark())
                .setPayAmount(submitDTO.getPayAmount())
                .setTotalQuantity(orderItems.stream().map(item -> item.getCount()).reduce(0, (x, y) -> x + y))
                .setTotalAmount(orderItems.stream().map(item -> item.getPrice() * item.getCount()).reduce(0l, (x, y) -> x + y))
                .setGmtCreate(new Date());
        this.save(order);

        // 创建订单商品
        List<OmsOrderItem> orderItemList = orderItems.stream().map(item -> OmsOrderItem.builder()
                .orderId(order.getId())
                .skuId(item.getSkuId())
                .skuName(item.getSkuName())
                .skuPrice(item.getPrice())
                .skuPic(item.getPic())
                .skuQuantity(item.getCount())
                .skuTotalPrice(item.getCount() * item.getPrice())
                .skuCode(item.getSkuCode())
                .build()).collect(Collectors.toList());
        orderItemService.saveBatch(orderItemList);

        // 将订单放入延时队列，超时未支付由交换机order.exchange切换到死信队列完成系统自动关单
        log.info("订单超时取消RabbitMQ消息发送，订单SN：{}", orderToken);
        rabbitTemplate.convertAndSend("order.exchange", "order.create", orderToken);

        OrderSubmitVO submitVO = new OrderSubmitVO();
        submitVO.setOrderId(order.getId());
        submitVO.setOrderSn(order.getOrderSn());
        log.info("订单提交响应：{}", submitVO.toString());
        return submitVO;
    }


    /**
     * 订单支付
     *
     * @param orderId
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public boolean pay(Long orderId) {

        OmsOrder order = this.getById(orderId);
        if (order != null && !OrderStatusEnum.PENDING_PAYMENT.getCode().equals(order.getStatus())) {
            throw new BizException("支付失败，请检查订单状态");
        }

        // 扣减余额
        Long userId = RequestUtils.getUserId();
        Long payAmount = order.getPayAmount();
        Result deductBalanceResult = memberFeignClient.deductBalance(userId, payAmount);
        if (!Result.isSuccess(deductBalanceResult)) {
            throw new BizException("扣减账户余额失败");
        }

        // 扣减库存
        Result deductStockResult = skuFeignService.deductStock(order.getOrderSn());
        if (!Result.isSuccess(deductStockResult)) {
            throw new BizException("扣减商品库存失败");
        }

        // 更新订单状态
        order.setStatus(OrderStatusEnum.PAID.getCode());
        order.setPayType(PayTypeEnum.BALANCE.getCode());
        order.setPayTime(new Date());
        this.updateById(order);

        // 支付成功删除购物车已勾选的商品
        cartService.removeCheckedItem();

        return true;
    }

    @Override
    public boolean closeOrder(String orderToken) {
        log.info("=======================订单关闭，订单SN：{}=======================", orderToken);
        OmsOrder order = this.getOne(new LambdaQueryWrapper<OmsOrder>()
                .eq(OmsOrder::getOrderSn, orderToken));
        if (order == null || !OrderStatusEnum.PENDING_PAYMENT.getCode().equals(order.getStatus())) {
            return false;
        }
        order.setStatus(OrderStatusEnum.AUTO_CANCEL.getCode());
        return this.updateById(order);
    }

    @Override
    public boolean cancelOrder(Long id) {
        log.info("=======================订单取消，订单ID：{}=======================", id);
        OmsOrder order = this.getById(id);

        if (order != null && !OrderStatusEnum.PENDING_PAYMENT.getCode().equals(order.getStatus())) {
            throw new BizException("取消失败，订单状态不支持取消"); // 通过自定义异常，将异常信息抛出由异常处理器捕获显示给前端页面
        }
        order.setStatus(OrderStatusEnum.USER_CANCEL.getCode());
        boolean result = this.updateById(order);
        if (result) {
            // 释放被锁定的库存
            Result unlockResult = skuFeignService.unlockStock(order.getOrderSn());
            if (!Result.isSuccess(unlockResult)) {
                throw new BizException(unlockResult.getMsg());
            }
            result = true;
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteOrder(Long id) {
        log.info("=======================订单删除，订单ID：{}=======================", id);
        OmsOrder order = this.getById(id);
        if (
                order != null &&
                        !OrderStatusEnum.AUTO_CANCEL.getCode().equals(order.getStatus()) &&
                        !OrderStatusEnum.USER_CANCEL.getCode().equals(order.getStatus())
        ) {
            throw new BizException("订单删除失败，订单不存在或订单状态不支持删除");
        }
        return this.removeById(id);
    }


    @Override
    public IPage<OmsOrder> list(Page<OmsOrder> page, OmsOrder order) {
        List<OmsOrder> list = this.baseMapper.list(page, order);
        page.setRecords(list);
        return page;
    }

}
