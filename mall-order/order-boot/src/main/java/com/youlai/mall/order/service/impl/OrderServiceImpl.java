package com.youlai.mall.order.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.binarywang.wxpay.bean.notify.SignatureHeader;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyV3Result;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyV3Result;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderV3Request;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import com.github.binarywang.wxpay.bean.result.enums.TradeTypeEnum;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.youlai.common.constant.RedisConstants;
import com.youlai.common.rabbitmq.constant.RabbitMqConstants;
import com.youlai.common.security.util.SecurityUtils;
import com.youlai.common.core.exception.BusinessException;
import com.youlai.mall.order.config.property.WxPayProperties;
import com.youlai.mall.order.converter.OrderConverter;
import com.youlai.mall.order.enums.ApprovalStatusEnum;
import com.youlai.mall.order.enums.OrderStatusEnum;
import com.youlai.mall.order.enums.OrderTypeEnum;
import com.youlai.mall.order.enums.PaymentMethodEnum;
import com.youlai.mall.order.mapper.OrderMapper;
import com.youlai.mall.order.model.bo.OrderBO;
import com.youlai.mall.order.model.dto.OrderItemDTO;
import com.youlai.mall.order.model.entity.OmsOrder;
import com.youlai.mall.order.model.entity.OmsOrderItem;
import com.youlai.mall.order.model.entity.OrderRefund;
import com.youlai.mall.order.model.form.OrderPayForm;
import com.youlai.mall.order.model.form.OrderRefundApprovalForm;
import com.youlai.mall.order.model.form.OrderRefundForm;
import com.youlai.mall.order.model.form.OrderSubmitForm;
import com.youlai.mall.order.model.query.OrderPageQuery;
import com.youlai.mall.order.model.vo.OrderPageAdminVO;
import com.youlai.mall.order.model.vo.OrderConfirmVO;
import com.youlai.mall.order.model.vo.OrderPageAppVO;
import com.youlai.mall.order.service.OrderItemService;
import com.youlai.mall.order.service.OrderRefundService;
import com.youlai.mall.order.service.OrderService;
import com.youlai.mall.order.util.OrderUtils;
import com.youlai.mall.product.api.SkuFeignClient;
import com.youlai.mall.product.model.dto.LockSkuDTO;
import com.youlai.mall.product.model.dto.SkuDTO;
import com.youlai.mall.member.api.MemberFeignClient;
import com.youlai.mall.member.dto.CartItemDTO;
import com.youlai.mall.member.dto.MemberAddressDTO;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;


/**
 * 订单业务实现类
 *
 * @author Ray
 * @since 2.0.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OmsOrder> implements OrderService {

    private final OrderConverter orderConverter;
    private final OrderItemService orderItemService;
    private final OrderRefundService orderRefundService;

    private final MemberFeignClient memberFeignClient;
    private final SkuFeignClient skuFeignClient;

    private final WxPayProperties wxPayProperties;
    private final WxPayService wxPayService;

    private final RabbitTemplate rabbitTemplate;
    private final StringRedisTemplate redisTemplate;
    private final RedissonClient redissonClient;
    private final ThreadPoolExecutor threadPoolExecutor;

    /**
     * 【Admin】订单分页列表
     *
     * @param queryParams {@link OrderPageQuery}
     * @return {@link OrderPageAdminVO}
     */
    @Override
    public IPage<OrderPageAdminVO> listAdminPagedOrders(OrderPageQuery queryParams) {
        Page<OrderBO> boPage = this.baseMapper.listAdminPagedOrders(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams);

        return orderConverter.toPageVo(boPage);
    }

    /**
     * 【App】订单分页列表
     */
    @Override
    public IPage<OrderPageAppVO> listAppPagedOrders(OrderPageQuery queryParams) {
        Page<OrderBO> boPage = this.baseMapper.listAdminPagedOrders(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams);
        return orderConverter.toAppPageVo(boPage);
    }


    /**
     * 订单确认 → 进入创建订单页面
     * <p>
     * 获取购买商品明细、用户默认收货地址、防重提交唯一token
     * 进入订单创建页面有两个入口，1：立即购买；2：购物车结算
     *
     * @param skuId 商品ID(直接购买传值)
     * @return {@link OrderConfirmVO}
     */
    @Override
    public OrderConfirmVO confirmOrder(Long skuId) {

        Long memberId = SecurityUtils.getMemberId();

        // 解决子线程无法获取HttpServletRequest请求对象中数据的问题
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        RequestContextHolder.setRequestAttributes(attributes, true);

        // 获取订单商品
        CompletableFuture<List<OrderItemDTO>> getOrderItemsFuture = CompletableFuture.supplyAsync(
                        () -> this.getOrderItems(skuId), threadPoolExecutor)
                .exceptionally(ex -> {
                    log.error("Failed to get order items: {}", ex.toString());
                    return Collections.emptyList();
                });

        // 用户收货地址
        CompletableFuture<List<MemberAddressDTO>> getMemberAddressFuture = CompletableFuture.supplyAsync(
                        memberFeignClient::getCurrentMemberAddresses, threadPoolExecutor)
                .exceptionally(ex -> {
                    log.error("Failed to get addresses for memberId {} : {}", memberId, ex.toString());
                    return Collections.emptyList();
                });

        // 生成唯一令牌,防止重复提交(原理：提交会消耗令牌，令牌被消耗无法再次提交)
        CompletableFuture<String> generateOrderTokenFuture = CompletableFuture.supplyAsync(() -> {
            String orderToken = OrderUtils.generateTradeNo(memberId, OrderTypeEnum.WECHAT_ORDER);
            redisTemplate.opsForValue().set(RedisConstants.ORDER_TOKEN_PREFIX + orderToken, orderToken);
            return orderToken;
        }, threadPoolExecutor).exceptionally(ex -> {
            log.error("Failed to generate order token .");
            return null;
        });

        CompletableFuture.allOf(getOrderItemsFuture, getMemberAddressFuture, generateOrderTokenFuture).join();

        OrderConfirmVO orderConfirmVO = new OrderConfirmVO();
        orderConfirmVO.setOrderItems(getOrderItemsFuture.join());
        orderConfirmVO.setAddresses(getMemberAddressFuture.join());
        orderConfirmVO.setOrderToken(generateOrderTokenFuture.join());

        log.info("Order confirm response for skuId {}: {}", skuId, orderConfirmVO);
        return orderConfirmVO;
    }

    /**
     * 订单提交
     *
     * @param submitForm {@link OrderSubmitForm}
     * @return 订单编号
     */
    @Override
    @GlobalTransactional
    public String submitOrder(OrderSubmitForm submitForm) {
        log.info("订单提交参数:{}", JSONUtil.toJsonStr(submitForm));
        String orderToken = submitForm.getOrderToken();

        // 1. 判断订单是否重复提交(LUA脚本保证获取和删除的原子性，成功返回1，否则返回0)
        String lockAcquireScript = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Long lockAcquired = this.redisTemplate.execute(
                new DefaultRedisScript<>(lockAcquireScript, Long.class),
                Collections.singletonList(RedisConstants.ORDER_TOKEN_PREFIX + orderToken),
                orderToken
        );
        Assert.isTrue(lockAcquired != null && lockAcquired.equals(1L), "订单重复提交，请刷新页面后重试");

        // 2. 订单商品校验 (PS：校验进入订单确认页面到提交过程商品(价格、上架状态)变化)
        List<OrderSubmitForm.OrderItem> orderItems = submitForm.getOrderItems();
        List<Long> skuIds = orderItems.stream()
                .map(OrderSubmitForm.OrderItem::getSkuId)
                .collect(Collectors.toList());
        List<SkuDTO> skuList;
        try {
            skuList = skuFeignClient.listSkusByIds(skuIds);
        } catch (Exception e) {
            log.error("Failed to get sku info list: {}", e.toString());
            skuList = Collections.emptyList();
        }
        for (OrderSubmitForm.OrderItem item : orderItems) {
            SkuDTO skuInfo = skuList.stream().filter(sku -> sku.getId().equals(item.getSkuId()))
                    .findFirst()
                    .orElse(null);
            Assert.isTrue(skuInfo != null, "商品({})已下架或删除");
            Assert.isTrue(item.getSkuPrice().compareTo(skuInfo.getPrice()) == 0, "商品({})价格发生变动，请刷新页面", item.getSkuName());
        }

        // 3. 校验库存并锁定库存
        List<LockSkuDTO> lockSkuList = orderItems.stream()
                .map(item -> new LockSkuDTO(item.getSkuId(), item.getSkuQuantity()))
                .collect(Collectors.toList());

        boolean lockStockResult = skuFeignClient.lockStock(orderToken, lockSkuList);
        Assert.isTrue(lockStockResult, "订单提交失败：锁定商品库存失败！");

        // 4. 生成订单
        boolean result = this.saveOrder(submitForm);
        log.info("order ({}) create result:{}", orderToken, result);
        return orderToken;
    }


    /**
     * 创建订单
     *
     * @param submitForm 订单提交表单对象
     * @return 是否成功
     */
    private boolean saveOrder(OrderSubmitForm submitForm) {
        // 保存订单
        OmsOrder order = orderConverter.toEntity(submitForm);
        order.setStatus(OrderStatusEnum.PENDING_PAYMENT.getValue());
        order.setMemberId(SecurityUtils.getMemberId());
        boolean result = this.save(order);

        if (result) {
            // 保存订单总得商品明细
            List<OrderSubmitForm.OrderItem> formOrderItems = submitForm.getOrderItems();
            List<OmsOrderItem> orderItems = formOrderItems.stream().map(formOrderItem -> {
                        OmsOrderItem entity = new OmsOrderItem();
                        BeanUtil.copyProperties(formOrderItem, entity);
                        entity.setTotalFee(formOrderItem.getSkuPrice() * formOrderItem.getSkuQuantity());
                        entity.setOrderId(order.getId());
                        return entity;
                    }
            ).toList();
            result = orderItemService.saveBatch(orderItems);

            if (result) {
                // 发送RabbitMQ消息处理订单超时未支付
                rabbitTemplate.convertAndSend(RabbitMqConstants.ORDER_EXCHANGE, RabbitMqConstants.ORDER_CLOSE_DELAY_QUEUE, submitForm.getOrderToken());
            }
        }
        return result;
    }


    /**
     * 订单支付
     * <p>
     * 余额支付：库存、余额、订单处理
     * 微信支付：生成微信支付调起参数，订单、库存、余额处理在支付回调
     */
    @Override
    @GlobalTransactional
    public <T> T payOrder(OrderPayForm paymentForm) {
        String orderNo = paymentForm.getOrderNo();
        OmsOrder order = this.getOne(new LambdaQueryWrapper<OmsOrder>()
                .eq(OmsOrder::getOrderNo, orderNo)
        );
        Assert.isTrue(order != null && OrderStatusEnum.PENDING_PAYMENT.getValue().equals(order.getStatus()),
                "订单不存在或已支付");

        RLock lock = redissonClient.getLock(RedisConstants.ORDER_PAYMENT_LOCK_PREFIX + order.getOrderNo());
        try {
            lock.lock();
            T result;
            switch (paymentForm.getPaymentMethod()) {
                case WX_JSAPI:
                    result = (T) wxJsapiPay(order.getOrderNo(), order.getPaymentAmount());
                    break;
                default:
                    result = (T) balancePay(order);
                    break;
            }
            return result;
        } finally {
            //释放锁
            if (lock.isLocked()) {
                lock.unlock();
            }
        }
    }

    /**
     * 订单退款
     *
     * @param refundForm 退款表单
     */
    @Override
    public boolean refundApply(OrderRefundForm refundForm) {

        Long orderId = refundForm.getOrderId();
        OmsOrder order = this.getById(orderId);
        Assert.isTrue(order != null, "订单不存在");
        // 添加退款申请记录
        String reason = refundForm.getReason();
        Long refundFee = refundForm.getRefundFee();

        OrderRefund orderRefund = new OrderRefund();
        orderRefund.setOrderId(orderId);
        orderRefund.setReason(reason);
        orderRefund.setRefundFee(refundFee);
        orderRefund.setTotalFee(order.getTotalFee());
        orderRefund.setStatus(ApprovalStatusEnum.WAITING_APPROVAL.getValue());
        return orderRefundService.save(orderRefund);
    }

    /**
     * 订单退款审批
     *
     * @param refundApprovalForm 退款审批表单
     */
    @Override
    public boolean refundApproval(OrderRefundApprovalForm refundApprovalForm) {
        Long refundId = refundApprovalForm.getId();
        Integer status = refundApprovalForm.getStatus();

        // 查询退款申请记录
        OrderRefund orderRefund = orderRefundService.getById(refundId);
        Assert.isTrue(orderRefund != null, "退款申请记录不存在");

        // 查询订单信息
        OmsOrder order = this.getById(orderRefund.getOrderId());
        Assert.isTrue(order != null, "订单不存在");

        orderRefund.setStatus(status);
        orderRefund.setApproverId(SecurityUtils.getUserId());
        orderRefund.setApprovalTime(LocalDateTime.now());
        boolean result = orderRefundService.updateById(orderRefund);

        // 审批通过，执行退款
        if (result && ApprovalStatusEnum.APPROVED.getValue().equals(status)) {
            String outRefundNo = OrderUtils.generateTradeNo(orderRefund.getId(), OrderTypeEnum.WECHAT_REFUND);
            WxPayRefundRequest request = new WxPayRefundRequest();
            request.setOutTradeNo(order.getOutTradeNo());
            request.setOutRefundNo(outRefundNo);
            request.setTotalFee(Convert.toInt(orderRefund.getTotalFee()));
            request.setRefundFee(Convert.toInt(orderRefund.getRefundFee()));
            request.setNotifyUrl(wxPayProperties.getRefundNotifyUrl());
            WxPayRefundResult refundResult;
            try {
                refundResult = wxPayService.refund(request);
            } catch (WxPayException e) {
                log.error(e.getMessage(), e);
                throw new BusinessException("订单（" + order.getOrderNo() + "）退款失败");
            }
            if (WxPayConstants.ResultCode.SUCCESS.equals(refundResult.getResultCode())) {
                order.setStatus(OrderStatusEnum.REFUND_PENDING.getValue());
                order.setOutRefundNo(outRefundNo);
                order.setRefundId(refundResult.getRefundId());
                return this.updateById(order);
            }
        }
        return result;
    }


    /**
     * 余额支付
     *
     * @param order 订单
     * @return 是否成功
     */
    private Boolean balancePay(OmsOrder order) {
        // 扣减余额
        boolean balanceDeducted = memberFeignClient.deductMemberBalance(order.getPaymentAmount());
        Assert.isTrue(balanceDeducted, "订单支付失败：余额扣减失败！");

        // 扣减库存
        boolean stockDeducted = skuFeignClient.deductStock(order.getOrderNo());
        Assert.isTrue(stockDeducted, "订单支付失败：库存扣减失败！");

        // 更新订单状态
        order.setStatus(OrderStatusEnum.PENDING_SHIPMENT.getValue());
        order.setPaymentMethod(PaymentMethodEnum.BALANCE.getValue());
        order.setPaymentTime(new Date());
        boolean result = this.updateById(order);
        if (result) {
            // 支付成功删除购物车已勾选的商品
            rabbitTemplate
                    .convertAndSend(RabbitMqConstants.CART_EXCHANGE, RabbitMqConstants.CART_REMOVE_ROUTING_KEY, order.getOrderNo());
        }
        return result;
    }


    /**
     * 微信支付调起
     *
     * @param orderNo       订单编号
     * @param paymentAmount 支付金额
     * @return 微信支付调起参数
     */
    private WxPayUnifiedOrderV3Result.JsapiResult wxJsapiPay(String orderNo, Long paymentAmount) {
        Long memberId = SecurityUtils.getMemberId();
        // 如果已经有outTradeNo了就先进行关单
        if (StrUtil.isNotBlank(orderNo)) {
            try {
                wxPayService.closeOrderV3(orderNo);
            } catch (WxPayException e) {
                log.error(e.getMessage(), e);
                throw new BusinessException("微信关单异常");
            }
        }

        // 更新订单状态
        boolean result = this.update(new LambdaUpdateWrapper<OmsOrder>()
                .set(OmsOrder::getPaymentMethod, PaymentMethodEnum.WX_JSAPI.getValue())
                .eq(OmsOrder::getOrderNo, orderNo)
        );

        String openId = memberFeignClient.getMemberOpenId(memberId);

        WxPayUnifiedOrderV3Request wxRequest = new WxPayUnifiedOrderV3Request()
                .setAppid(wxPayProperties.getAppId())
                .setOutTradeNo(orderNo)
                .setAmount(new WxPayUnifiedOrderV3Request
                        .Amount()
                        .setTotal(Math.toIntExact(paymentAmount))
                )
                .setPayer(
                        new WxPayUnifiedOrderV3Request.Payer()
                                .setOpenid(openId)
                )
                .setDescription("赅买-订单编号：" + orderNo)
                .setNotifyUrl(wxPayProperties.getNotifyUrl());
        WxPayUnifiedOrderV3Result.JsapiResult jsapiResult;
        try {
            jsapiResult = wxPayService.createOrderV3(TradeTypeEnum.JSAPI, wxRequest);
        } catch (WxPayException e) {
            log.error(e.getMessage(), e);
            throw new BusinessException("微信统一下单异常");
        }
        return jsapiResult;
    }

    /**
     * 关闭订单
     *
     * @param orderNo 订单编号
     * @return 是否成功 true|false
     */
    @Override
    public boolean closeOrder(String orderNo) {

        return this.update(new LambdaUpdateWrapper<OmsOrder>()
                .eq(OmsOrder::getOrderNo, orderNo)
                .eq(OmsOrder::getStatus, OrderStatusEnum.PENDING_PAYMENT.getValue())
                .set(OmsOrder::getStatus, OrderStatusEnum.CANCELLED.getValue())
        );
    }

    /**
     * 删除订单
     *
     * @param orderId 订单ID
     * @return true/false
     */
    @Override
    public boolean deleteOrder(Long orderId) {
        OmsOrder order = this.getById(orderId);
        Assert.isTrue(order != null, "删除失败,订单不存在！");

        Assert.isTrue(
                OrderStatusEnum.CANCELLED.getValue().equals(order.getStatus())
                        || OrderStatusEnum.PENDING_PAYMENT.getValue().equals(order.getStatus())
                ,
                "当前状态订单不能删除"
        );

        return this.removeById(orderId);
    }

    /**
     * 处理微信支付结果通知
     *
     * @param signatureHeader 签名头信息
     * @param notifyData      通知数据
     * @throws WxPayException 微信支付异常
     */
    @Override
    public void handleWxPayOrderNotify(SignatureHeader signatureHeader, String notifyData) throws WxPayException {
        // 记录开始处理支付结果通知的日志
        log.info("开始处理订单支付结果通知");
        // 解密支付通知数据，获取支付结果
        WxPayOrderNotifyV3Result.DecryptNotifyResult result = this.wxPayService.parseOrderNotifyV3Result(
                notifyData,
                signatureHeader
        ).getResult();

        // 记录支付通知解密成功的日志，并打印解密结果
        log.debug("支付通知解密成功：{}", JSONUtil.toJsonStr(result));

        // 获取商户订单号
        String outTradeNo = result.getOutTradeNo();
        log.info("商户订单号：{}", outTradeNo);
        // 根据商户订单号查询订单信息
        OmsOrder order = this.getOne(new LambdaQueryWrapper<OmsOrder>()
                .eq(OmsOrder::getOutTradeNo, outTradeNo)
        );
        Assert.isTrue(order != null, "订单不存在");

        // 如果支付状态为成功，更新订单状态和相关信息
        if (WxPayConstants.WxpayTradeStatus.SUCCESS.equals(result.getTradeState())) {
            // 设置订单状态为待发货
            order.setStatus(OrderStatusEnum.PENDING_SHIPMENT.getValue());
            // 设置交易流水号
            order.setTransactionId(result.getTransactionId());
            // 设置支付时间为当前时间
            order.setPaymentTime(new Date());
            // 更新订单信息
            this.updateById(order);

            // 支付成功后删除购物车中已勾选的商品
            rabbitTemplate
                    .convertAndSend(RabbitMqConstants.CART_EXCHANGE,
                            RabbitMqConstants.CART_REMOVE_ROUTING_KEY,
                            order.getOrderNo());
        }
    }


    /**
     * 处理微信退款结果通知
     *
     * @param signatureHeader 签名头信息
     * @param notifyData      通知数据
     * @throws WxPayException 微信支付异常
     */
    @Override
    public void handleWxPayRefundNotify(SignatureHeader signatureHeader, String notifyData) throws WxPayException {
        log.info("开始处理退款结果通知");
        // 解密支付通知内容
        WxPayRefundNotifyV3Result.DecryptNotifyResult result = this.wxPayService.parseRefundNotifyV3Result(notifyData, signatureHeader).getResult();
        log.debug("退款通知解密成功：{}", JSONUtil.toJsonStr(result));
        // 获取商户订单号
        String outTradeNo = result.getOutTradeNo();
        log.info("商户订单号：{}", outTradeNo);
        // 根据商户订单号查询订单信息
        OmsOrder order = this.getOne(new LambdaQueryWrapper<OmsOrder>()
                .eq(OmsOrder::getOutTradeNo, outTradeNo)
        );
        Assert.isTrue(order != null, "订单不存在");

        // 退款成功处理
        if (WxPayConstants.RefundStatus.SUCCESS.equals(result.getRefundStatus())) {
            order.setStatus(OrderStatusEnum.REFUNDED.getValue());
            order.setRefundId(result.getRefundId());
            this.updateById(order);
        }
    }

    /**
     * 获取订单的商品明细信息
     * <p>
     * 创建订单两种方式，1：直接购买；2：购物车结算
     *
     * @param skuId 直接购买必有值，购物车结算必没值
     * @return 订单商品明细列表
     */
    private List<OrderItemDTO> getOrderItems(Long skuId) {
        List<OrderItemDTO> orderItems;
        // 直接购买
        if (skuId != null) {
            orderItems = new ArrayList<>();
            SkuDTO skuDTO = skuFeignClient.getSkuById(skuId);
            OrderItemDTO orderItemDTO = new OrderItemDTO();
            orderItemDTO.setSkuId(skuId);
            BeanUtil.copyProperties(skuDTO, orderItemDTO);
            orderItemDTO.setSkuId(skuDTO.getId());
            // 直接购买商品的数量为1
            orderItemDTO.setQuantity(1);
            orderItems.add(orderItemDTO);
        } else {
            // 购物车点击“结算”获取订单明细
            List<CartItemDTO> cartItems = memberFeignClient.getCurrentMemberCartItems();
            orderItems = cartItems.stream()
                    .filter(CartItemDTO::getChecked)
                    .map(cartItem -> {
                        OrderItemDTO orderItemDTO = new OrderItemDTO();
                        BeanUtil.copyProperties(cartItem, orderItemDTO);
                        return orderItemDTO;
                    }).collect(Collectors.toList());
        }

        return orderItems;

    }


}
