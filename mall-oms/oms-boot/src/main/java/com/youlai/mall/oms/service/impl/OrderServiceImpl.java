package com.youlai.mall.oms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.binarywang.wxpay.bean.notify.SignatureHeader;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyV3Result;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyV3Result;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderV3Request;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import com.github.binarywang.wxpay.bean.result.enums.TradeTypeEnum;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.youlai.common.redis.BusinessSnGenerator;
import com.youlai.common.result.Result;
import com.youlai.common.security.util.SecurityUtils;
import com.youlai.common.web.exception.BizException;
import com.youlai.mall.oms.config.WxPayProperties;
import com.youlai.mall.oms.common.enums.OrderStatusEnum;
import com.youlai.mall.oms.common.enums.PayTypeEnum;
import com.youlai.mall.oms.converter.OrderConverter;
import com.youlai.mall.oms.converter.OrderItemConverter;
import com.youlai.mall.oms.mapper.OrderMapper;
import com.youlai.mall.oms.pojo.dto.CartItemDTO;
import com.youlai.mall.oms.pojo.dto.OrderItemDTO;
import com.youlai.mall.oms.pojo.entity.OmsOrder;
import com.youlai.mall.oms.pojo.entity.OmsOrderItem;
import com.youlai.mall.oms.pojo.form.OrderSubmitForm;
import com.youlai.mall.oms.pojo.query.OrderPageQuery;
import com.youlai.mall.oms.pojo.vo.OrderConfirmVO;
import com.youlai.mall.oms.pojo.vo.OrderSubmitResultVO;
import com.youlai.mall.oms.service.CartService;
import com.youlai.mall.oms.service.OrderItemService;
import com.youlai.mall.oms.service.OrderService;
import com.youlai.mall.pms.api.SkuFeignClient;
import com.youlai.mall.pms.pojo.dto.CheckPriceDTO;
import com.youlai.mall.pms.pojo.dto.SkuDTO;
import com.youlai.mall.pms.pojo.dto.LockStockDTO;
import com.youlai.mall.ums.api.MemberFeignClient;
import com.youlai.mall.ums.dto.MemberAddressDTO;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import static com.youlai.mall.oms.common.constant.OmsConstants.*;

/**
 * 订单业务实现类
 *
 * @author haoxr
 * @date 2022/2/12
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OmsOrder> implements OrderService {

    private final WxPayProperties wxPayProperties;
    private final CartService cartService;
    private final OrderItemService orderItemService;
    private final RabbitTemplate rabbitTemplate;
    private final StringRedisTemplate redisTemplate;
    private final ThreadPoolExecutor threadPoolExecutor;
    private final MemberFeignClient memberFeignClient;
    private final BusinessSnGenerator businessSnGenerator;
    private final SkuFeignClient skuFeignClient;
    private final RedissonClient redissonClient;
    private final WxPayService wxPayService;
    private final OrderConverter orderConverter;
    private final OrderItemConverter orderItemConverter;

    /**
     * 订单分页列表
     */
    @Override
    public IPage<OmsOrder> listOrderPages(OrderPageQuery queryParams) {
        Page<OmsOrder> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        List<OmsOrder> list = this.baseMapper.listOrderPages(page, queryParams);
        page.setRecords(list);
        return page;
    }

    /**
     * 订单确认 → 进入创建订单页面
     * <p>
     * 获取购买商品明细、用户默认收货地址、防重提交唯一token
     * 进入订单创建页面有两个入口，1：立即购买；2：购物车结算
     *
     * @param skuId 直接购买必填，购物车结算不填
     * @return
     */
    @Override
    public OrderConfirmVO confirmOrder(Long skuId) {
        OrderConfirmVO orderConfirmVO = new OrderConfirmVO();
        // 获取原请求线程的参数
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        Long memberId = SecurityUtils.getMemberId();
        // 获取订单的商品明细信息
        CompletableFuture<Void> getOrderItemsFuture = CompletableFuture.runAsync(() -> {
            // 请求参数传递给子线程
            RequestContextHolder.setRequestAttributes(attributes);
            List<OrderItemDTO> orderItems = this.getOrderItems(skuId, memberId);
            orderConfirmVO.setOrderItems(orderItems);
        }, threadPoolExecutor);

        // 获取会员收获地址
        CompletableFuture<Void> getMemberAddressFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(attributes);
            Result<List<MemberAddressDTO>> getMemberAddressResult = memberFeignClient.listMemberAddresses(memberId);
            List<MemberAddressDTO> memberAddresses;
            if (Result.isSuccess(getMemberAddressResult) && (memberAddresses = getMemberAddressResult.getData()) != null) {
                orderConfirmVO.setAddresses(memberAddresses);
            } else {
                orderConfirmVO.setAddresses(Collections.EMPTY_LIST);
            }
        }, threadPoolExecutor);

        // 进入订单确认页面生成唯一token,订单提交根据此token判断是否重复提交
        CompletableFuture<Void> getOrderTokenFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(attributes);
            String orderToken = businessSnGenerator.generateSerialNo("ORDER");
            orderConfirmVO.setOrderToken(orderToken);
            redisTemplate.opsForValue().set(ORDER_RESUBMIT_LOCK_PREFIX + orderToken, orderToken);
        }, threadPoolExecutor);

        CompletableFuture.allOf(getOrderItemsFuture, getMemberAddressFuture, getOrderTokenFuture).join();
        log.info("订单确认响应：{}", orderConfirmVO);
        return orderConfirmVO;
    }

    /**
     * 订单提交
     */
    @Override
    @GlobalTransactional
    public OrderSubmitResultVO submitOrder(OrderSubmitForm orderSubmitForm) {
        log.info("订单提交数据:{}", JSONUtil.toJsonStr(orderSubmitForm));
        // 订单基础信息校验
        List<OrderItemDTO> orderItems = orderSubmitForm.getOrderItems();
        Assert.isTrue(CollectionUtil.isNotEmpty(orderItems), "订单没有商品");

        // 订单重复提交校验
        String orderSn = orderSubmitForm.getOrderToken();
        Long releaseLockResult = this.redisTemplate.execute(
                new DefaultRedisScript<>(
                        "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end", Long.class
                ),
                Collections.singletonList(ORDER_RESUBMIT_LOCK_PREFIX + orderSn),
                orderSn
        );  // 释放锁成功则返回1
        Assert.isTrue(releaseLockResult.equals(1l), "订单重复提交，请刷新页面后重试");

        // 订单验价
        List<CheckPriceDTO.OrderSku> orderSkus = orderItems.stream()
                .map(orderItem -> new CheckPriceDTO.OrderSku(orderItem.getSkuId(), orderItem.getCount())
                ).collect(Collectors.toList());

        CheckPriceDTO checkPriceDTO = new CheckPriceDTO(orderSn, orderSubmitForm.getTotalAmount(), orderSkus);
        Result<Boolean> checkPriceResult = skuFeignClient.checkPrice(checkPriceDTO);
        Assert.isTrue(Result.isSuccess(checkPriceResult) && Boolean.TRUE.equals(checkPriceResult.getData()),
                "当前页面已过期，请重新刷新页面再提交");

        // 锁定订单商品的库存
        List<LockStockDTO.LockedSku> lockedSkus = orderItems.stream()
                .map(orderItem -> new LockStockDTO.LockedSku(orderItem.getSkuId(), orderItem.getCount()))
                .collect(Collectors.toList());

        LockStockDTO lockStockDTO = new LockStockDTO(orderSn, lockedSkus);

        Result lockStockResult = skuFeignClient.lockStock(lockStockDTO);
        Assert.isTrue(Result.isSuccess(lockStockResult), "订单提交失败：锁定商品库存失败！");

        // 创建订单
        OmsOrder orderEntity = orderConverter.submitForm2Entity(orderSubmitForm);
        orderEntity.setStatus(OrderStatusEnum.UNPAID.getValue());
        orderEntity.setMemberId(SecurityUtils.getMemberId());
        boolean result = this.save(orderEntity);
        // 添加订单明细
        Long orderId = orderEntity.getId();
        if (result) {
            List<OmsOrderItem> itemEntities = orderItemConverter.dto2Entity(orderId, orderItems);
            result = orderItemService.saveBatch(itemEntities);
            if (result) {
                // 订单超时未支付关单
                rabbitTemplate.convertAndSend("order.exchange", "order.close.delay.routing.key", orderSn);
            }
        }
        Assert.isTrue(result, "订单提交失败");

        // 成功响应返回值构建
        OrderSubmitResultVO submitVO = new OrderSubmitResultVO(orderId, orderEntity.getOrderSn());
        return submitVO;
    }

    /**
     * 订单支付
     */
    @Override
    @GlobalTransactional
    public boolean payOrder(Long orderId) {

        OmsOrder order = this.getById(orderId);
        Assert.isTrue(order != null, "订单不存在");

        Assert.isTrue(OrderStatusEnum.UNPAID.getValue().equals(order.getStatus()), "订单不可支付，请检查订单状态");

        RLock lock = redissonClient.getLock(ORDER_LOCK_PREFIX + order.getOrderSn());
        try {

            lock.lock();
            // 扣减余额
            memberFeignClient.deductBalance(SecurityUtils.getMemberId(), order.getPayAmount());
            // 扣减库存
            skuFeignClient.deductStock(order.getOrderSn());
            // 修改订单状态 → 【已支付】
            order.setStatus(OrderStatusEnum.PAID.getValue());
            order.setPayType(PayTypeEnum.BALANCE.getValue());
            order.setPayTime(new Date());
            this.updateById(order);
            // 支付成功删除购物车已勾选的商品
            cartService.removeCheckedItem();
            return true;
        } finally {
            //释放锁
            if (lock.isLocked()) {
                lock.unlock();
            }
        }
    }

    /**
     * 微信支付
     *
     * @param appId
     * @param order
     * @return
     */
    private WxPayUnifiedOrderV3Result.JsapiResult wxJsapiPay(String appId, OmsOrder order) {
        Long memberId = SecurityUtils.getMemberId();
        Long payAmount = order.getPayAmount();
        // 如果已经有outTradeNo了就先进行关单
        if (PayTypeEnum.WX_JSAPI.getValue().equals(order.getPayType()) && StrUtil.isNotBlank(order.getOutTradeNo())) {
            try {
                wxPayService.closeOrderV3(order.getOutTradeNo());
            } catch (WxPayException e) {
                log.error(e.getMessage(), e);
                throw new BizException("微信关单异常");
            }
        }
        // 用户id前补零保证五位，对超出五位的保留后五位
        String userIdFilledZero = String.format("%05d", memberId);
        String fiveDigitsUserId = userIdFilledZero.substring(userIdFilledZero.length() - 5);
        // 在前面加上wxo（wx order）等前缀是为了人工可以快速分辨订单号是下单还是退款、来自哪家支付机构等
        // 将时间戳+3位随机数+五位id组成商户订单号，规则参考自<a href="https://tech.meituan.com/2016/11/18/dianping-order-db-sharding.html">大众点评</a>
        String outTradeNo = "wxo_" + System.currentTimeMillis() + RandomUtil.randomNumbers(3) + fiveDigitsUserId;
        log.info("商户订单号拼接完成：{}", outTradeNo);
        // 更新订单状态
        order.setPayType(PayTypeEnum.WX_JSAPI.getValue());
        order.setOutTradeNo(outTradeNo);
        this.updateById(order);

        String memberOpenId = memberFeignClient.getMemberOpenId(memberId).getData();

        WxPayUnifiedOrderV3Request wxRequest = new WxPayUnifiedOrderV3Request().setOutTradeNo(outTradeNo).setAppid(appId).setNotifyUrl(wxPayProperties.getPayNotifyUrl()).setAmount(new WxPayUnifiedOrderV3Request.Amount().setTotal(Math.toIntExact(payAmount))).setPayer(new WxPayUnifiedOrderV3Request.Payer().setOpenid(memberOpenId)).setDescription("赅买-订单编号" + order.getOrderSn());
        WxPayUnifiedOrderV3Result.JsapiResult jsapiResult;
        try {
            jsapiResult = wxPayService.createOrderV3(TradeTypeEnum.JSAPI, wxRequest);
        } catch (WxPayException e) {
            log.error(e.getMessage(), e);
            throw new BizException("微信统一下单异常");
        }
        return jsapiResult;
    }

    /**
     * 关闭订单(超时未支付)
     *
     * @param orderSn 订单编号
     * @return
     */
    @Override
    public boolean closeOrder(String orderSn) {
        OmsOrder order = this.getOne(new LambdaQueryWrapper<OmsOrder>()
                .eq(OmsOrder::getOrderSn, orderSn)
                .select(OmsOrder::getId, OmsOrder::getStatus)
        );
        Assert.isTrue(order != null, "订单不存在");
        boolean result;
        if (OrderStatusEnum.UNPAID.getValue().equals(order.getStatus())) {
            result = this.update(new LambdaUpdateWrapper<OmsOrder>()
                    .eq(OmsOrder::getId, order.getId())
                    .set(OmsOrder::getStatus, OrderStatusEnum.CANCELED.getValue()));
            // 关单成功释放锁定的商品库存
            rabbitTemplate.convertAndSend("stock.exchange", "stock.release.routing.key", orderSn);
        } else { // 订单非【待付款】状态无需关闭
            result = true;
        }
        return result;
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
                OrderStatusEnum.CANCELED.getValue().equals(order.getStatus())
                        || OrderStatusEnum.UNPAID.getValue().equals(order.getStatus())
                ,
                "当前状态订单不能删除"
        );

        return this.removeById(orderId);
    }

    @Override
    public void handleWxPayOrderNotify(SignatureHeader signatureHeader, String notifyData) throws WxPayException {
        log.info("开始处理支付结果通知");
        // 解密支付通知内容
        final WxPayOrderNotifyV3Result.DecryptNotifyResult result = this.wxPayService.parseOrderNotifyV3Result(notifyData, signatureHeader).getResult();
        log.debug("支付通知解密成功：[{}]", result.toString());
        // 根据商户订单号查询订单
        OmsOrder orderDO = this.getOne(new LambdaQueryWrapper<OmsOrder>()
                .eq(OmsOrder::getOutTradeNo, result.getOutTradeNo())
        );
        // 支付成功处理
        if (WxPayConstants.WxpayTradeStatus.SUCCESS.equals(result.getTradeState())) {
            orderDO.setStatus(OrderStatusEnum.PAID.getValue());
            orderDO.setTransactionId(result.getTransactionId());
            orderDO.setPayTime(new Date());
            this.updateById(orderDO);
        }
        log.info("账单更新成功");
        // 支付成功删除购物车已勾选的商品
        cartService.removeCheckedItem();
    }

    @Override
    public void handleWxPayRefundNotify(SignatureHeader signatureHeader, String notifyData) throws WxPayException {
        log.info("开始处理退款结果通知");
        // 解密支付通知内容
        final WxPayRefundNotifyV3Result.DecryptNotifyResult result = this.wxPayService.parseRefundNotifyV3Result(notifyData, signatureHeader).getResult();
        log.debug("退款通知解密成功：[{}]", result.toString());
        // 根据商户退款单号查询订单
        QueryWrapper<OmsOrder> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(OmsOrder::getOutTradeNo, result.getOutTradeNo());
        OmsOrder orderDO = this.getOne(wrapper);
        // 退款成功处理
        if (WxPayConstants.RefundStatus.SUCCESS.equals(result.getRefundStatus())) {
            orderDO.setStatus(OrderStatusEnum.COMPLETE.getValue());
            orderDO.setRefundId(result.getRefundId());
            this.updateById(orderDO);
        }
        log.info("账单更新成功");
    }


    /**
     * 获取订单的商品明细信息
     * <p>
     * 创建订单两种方式，1：直接购买；2：购物车结算
     *
     * @param skuId 直接购买必有值，购物车结算必没值
     * @return
     */
    private List<OrderItemDTO> getOrderItems(Long skuId, Long memberId) {
        List<OrderItemDTO> orderItems;
        if (skuId != null) {  // 直接购买
            orderItems = new ArrayList<>();
            SkuDTO skuDTO = skuFeignClient.getSkuInfo(skuId).getData();
            OrderItemDTO orderItemDTO = new OrderItemDTO();
            BeanUtil.copyProperties(skuDTO, orderItemDTO);

            orderItemDTO.setCount(1); // 直接购买商品的数量为1
            orderItems.add(orderItemDTO);
        } else { // 购物车结算
            List<CartItemDTO> cartItems = cartService.listCartItems(memberId);
            orderItems = cartItems.stream().filter(CartItemDTO::getChecked).map(cartItem -> {
                OrderItemDTO orderItemDTO = new OrderItemDTO();
                BeanUtil.copyProperties(cartItem, orderItemDTO);
                return orderItemDTO;
            }).collect(Collectors.toList());
        }
        return orderItems;
    }

}
