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
import com.youlai.common.web.exception.ApiException;
import com.youlai.mall.oms.config.WxPayProperties;
import com.youlai.mall.oms.dto.SeataOrderDTO;
import com.youlai.mall.oms.enums.OrderStatusEnum;
import com.youlai.mall.oms.enums.OrderSourceTypeEnum;
import com.youlai.mall.oms.enums.PayTypeEnum;
import com.youlai.mall.oms.mapper.OrderMapper;
import com.youlai.mall.oms.pojo.dto.CartItemDTO;
import com.youlai.mall.oms.pojo.dto.OrderItemDTO;
import com.youlai.mall.oms.pojo.entity.OmsOrder;
import com.youlai.mall.oms.pojo.entity.OmsOrderItem;
import com.youlai.mall.oms.pojo.form.OrderSubmitForm;
import com.youlai.mall.oms.pojo.query.OrderPageQuery;
import com.youlai.mall.oms.pojo.vo.OrderConfirmVO;
import com.youlai.mall.oms.pojo.vo.OrderSubmitVO;
import com.youlai.mall.oms.service.ICartService;
import com.youlai.mall.oms.service.IOrderItemService;
import com.youlai.mall.oms.service.IOrderService;
import com.youlai.mall.pms.api.SkuFeignClient;
import com.youlai.mall.pms.pojo.dto.CheckPriceDTO;
import com.youlai.mall.pms.pojo.dto.SkuInfoDTO;
import com.youlai.mall.pms.pojo.dto.LockStockDTO;
import com.youlai.mall.ums.api.MemberFeignClient;
import com.youlai.mall.ums.dto.MemberAddressDTO;
import io.seata.core.context.RootContext;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import static com.youlai.mall.oms.constant.OmsConstants.*;

/**
 * 订单业务实现类
 *
 * @author haoxr
 * @date 2022/2/12
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OmsOrder> implements IOrderService {

    private final WxPayProperties wxPayProperties;
    private final ICartService cartService;
    private final IOrderItemService orderItemService;
    private final RabbitTemplate rabbitTemplate;
    private final StringRedisTemplate redisTemplate;
    private final ThreadPoolExecutor threadPoolExecutor;
    private final MemberFeignClient memberFeignClient;
    private final BusinessSnGenerator businessSnGenerator;
    private final SkuFeignClient skuFeignClient;
    private final RedissonClient redissonClient;
    private final WxPayService wxPayService;

    /**
     * 订单分页列表
     *
     * @param queryParams
     * @return
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
            String orderToken = businessSnGenerator.generateSerialNo();
            orderConfirmVO.setOrderToken(orderToken);
            redisTemplate.opsForValue().set(ORDER_TOKEN_PREFIX + orderToken, orderToken);
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
    public OrderSubmitVO submitOrder(OrderSubmitForm orderSubmitForm) {
        log.info("订单提交数据:{}", JSONUtil.toJsonStr(orderSubmitForm));

        // 订单基础信息校验
        List<OrderItemDTO> orderItems = orderSubmitForm.getOrderItems();
        Assert.isTrue(CollectionUtil.isNotEmpty(orderItems), "订单没有商品");

        // 订单重复提交校验
        String orderToken = orderSubmitForm.getOrderToken();
        Long releaseLockResult = this.redisTemplate.execute(new DefaultRedisScript<>("if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end", Long.class), Collections.singletonList(ORDER_TOKEN_PREFIX + orderToken), orderToken);  // 释放锁成功则返回1
        Assert.isTrue(releaseLockResult.equals(1l), "订单不可重复提交");

        OmsOrder order;
        try {
            // 订单验价
            Long orderTotalAmount = orderSubmitForm.getTotalAmount();
            boolean checkResult = this.checkOrderPrice(orderTotalAmount, orderItems);
            Assert.isTrue(checkResult, "当前页面已过期，请重新刷新页面再提交");

            // 锁定商品库存
            this.lockStock(orderToken, orderItems);

            // 创建订单
            order = new OmsOrder().setOrderSn(orderToken) // 把orderToken赋值给订单编号
                    .setStatus(OrderStatusEnum.WAIT_PAY.getValue())
                    .setSourceType(OrderSourceTypeEnum.APP.getCode())
                    .setMemberId(SecurityUtils.getMemberId())
                    .setRemark(orderSubmitForm.getRemark())
                    .setPayAmount(orderSubmitForm.getPayAmount())
                    .setTotalQuantity(orderItems.stream()
                            .map(OrderItemDTO::getCount).reduce(0, Integer::sum))
                    .setTotalAmount(orderItems.stream().map(item -> item.getPrice() * item.getCount())
                            .reduce(0L, Long::sum));
            boolean result = this.save(order);

            // 添加订单明细
            if (result) {
                List<OmsOrderItem> saveOrderItems = orderItems.stream().map(orderFormItem -> {
                    OmsOrderItem omsOrderItem = new OmsOrderItem();
                    BeanUtil.copyProperties(orderFormItem, omsOrderItem);
                    omsOrderItem.setOrderId(order.getId());
                    omsOrderItem.setTotalAmount(orderFormItem.getPrice() * orderFormItem.getCount());
                    return omsOrderItem;
                }).collect(Collectors.toList());
                result = orderItemService.saveBatch(saveOrderItems);
                if (result) {
                    // 订单超时取消
                    rabbitTemplate.convertAndSend("order.exchange", "order.create.routing.key", orderToken);
                }
            }
            Assert.isTrue(result, "订单提交失败");
        } catch (Exception e) {
            redisTemplate.opsForValue().set(ORDER_TOKEN_PREFIX + orderToken, orderToken);
            throw new ApiException(e);
        }
        // 成功响应返回值构建
        OrderSubmitVO submitVO = new OrderSubmitVO();
        submitVO.setOrderId(order.getId());
        submitVO.setOrderSn(order.getOrderSn());
        return submitVO;
    }


    /**
     * 订单支付
     */
    @Override
    @GlobalTransactional
    public <T> T pay(Long orderId, String appId, PayTypeEnum payTypeEnum) {
        OmsOrder order = this.getById(orderId);
        Assert.isTrue(order != null, "订单不存在");
        Assert.isTrue(OrderStatusEnum.WAIT_PAY.getValue().equals(order.getStatus()), "订单不可支付，请检查订单状态");

        RLock lock = redissonClient.getLock(ORDER_SN_PREFIX + order.getOrderSn());
        try {

            lock.lock();
            T result;
            switch (payTypeEnum) {
                case WX_JSAPI:
                    result = (T) wxJsapiPay(appId, order);
                    break;
                default:
                    result = (T) balancePay(order);
                    break;
            }
            // 扣减库存
            Result<?> deductStockResult = skuFeignClient.deductStock(order.getOrderSn());
            Assert.isTrue(Result.isSuccess(deductStockResult), "扣减商品库存失败");
            return result;
        } finally {
            //释放锁
            if (lock.isLocked()) {
                lock.unlock();
            }
        }
    }


    /**
     * 余额支付
     *
     * @param order
     * @return
     */
    private Boolean balancePay(OmsOrder order) {
        // 扣减余额
        Long memberId = SecurityUtils.getMemberId();
        Long amount = order.getPayAmount();
        Result<?> deductBalanceResult = memberFeignClient.deductBalance(memberId, amount);
        Assert.isTrue(Result.isSuccess(deductBalanceResult), "扣减账户余额失败");

        // 更新订单状态
        order.setStatus(OrderStatusEnum.WAIT_SHIPPING.getValue());
        order.setPayType(PayTypeEnum.BALANCE.getValue());
        order.setPayTime(new Date());
        this.updateById(order);
        // 支付成功删除购物车已勾选的商品
        cartService.removeCheckedItem();

        return true;
    }


    private WxPayUnifiedOrderV3Result.JsapiResult wxJsapiPay(String appId, OmsOrder order) {
        Long memberId = SecurityUtils.getMemberId();
        Long payAmount = order.getPayAmount();
        // 如果已经有outTradeNo了就先进行关单
        if (PayTypeEnum.WX_JSAPI.getValue().equals(order.getPayType()) && StrUtil.isNotBlank(order.getOutTradeNo())) {
            try {
                wxPayService.closeOrderV3(order.getOutTradeNo());
            } catch (WxPayException e) {
                log.error(e.getMessage(), e);
                throw new ApiException("微信关单异常");
            }
        }
        // 用户id前补零保证五位，对超出五位的保留后五位
        String userIdFilledZero = String.format("%05d", memberId);
        String fiveDigitsUserId = userIdFilledZero.substring(userIdFilledZero.length() - 5);
        // 在前面加上wxo（weixin order）等前缀是为了人工可以快速分辨订单号是下单还是退款、来自哪家支付机构等
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
            throw new ApiException("微信统一下单异常");
        }
        return jsapiResult;
    }

    @Override
    public boolean closeOrder(String orderToken) {
        log.info("订单超时取消，orderToken:{}", orderToken);
        OmsOrder order = this.getOne(new LambdaQueryWrapper<OmsOrder>().eq(OmsOrder::getOrderSn, orderToken));
        if (order == null || !OrderStatusEnum.WAIT_PAY.getValue().equals(order.getStatus())) {
            return false;
        }
        // 如果已经有outTradeNo了就先进行关单
        if (PayTypeEnum.WX_JSAPI.getValue().equals(order.getPayType()) && StrUtil.isNotBlank(order.getOutTradeNo())) {
            try {
                wxPayService.closeOrderV3(order.getOutTradeNo());
                order.setOutTradeNo(null);
            } catch (WxPayException e) {
                log.error(e.getMessage(), e);
                throw new ApiException("微信关单异常");
            }
        }
        order.setStatus(OrderStatusEnum.CANCELED.getValue());
        return this.updateById(order);
    }

    @Override
    public boolean cancelOrder(Long id) {
        log.info("订单超时取消，订单ID：{}", id);
        OmsOrder order = this.getById(id);
        if (order == null) {
            throw new ApiException("订单不存在");
        }

        if (!OrderStatusEnum.WAIT_PAY.getValue().equals(order.getStatus())) {
            throw new ApiException("取消失败，订单状态不支持取消"); // 通过自定义异常，将异常信息抛出由异常处理器捕获显示给前端页面
        }
        // 如果已经有outTradeNo了就先进行关单
        if (PayTypeEnum.WX_JSAPI.getValue().equals(order.getPayType()) && StrUtil.isNotBlank(order.getOutTradeNo())) {
            try {
                wxPayService.closeOrderV3(order.getOutTradeNo());
                order.setOutTradeNo(null);
            } catch (WxPayException e) {
                log.error(e.getMessage(), e);
                throw new ApiException("微信关单异常");
            }
        }
        order.setStatus(OrderStatusEnum.CANCELED.getValue());
        boolean result = this.updateById(order);
        if (result) {
            // 释放被锁定的库存
            Result<?> unlockResult = skuFeignClient.unlockStock(order.getOrderSn());
            if (!Result.isSuccess(unlockResult)) {
                throw new ApiException(unlockResult.getMsg());
            }
        }
        return result;
    }

    @Override
    public boolean deleteOrder(Long id) {
        log.info("=======================订单删除，订单ID：{}=======================", id);
        OmsOrder order = this.getById(id);
        if (order != null && !OrderStatusEnum.CANCELED.getValue().equals(order.getStatus())) {
            throw new ApiException("订单删除失败，订单不存在或订单状态不支持删除");
        }
        return this.removeById(id);
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
            orderDO.setStatus(OrderStatusEnum.WAIT_SHIPPING.getValue());
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
            orderDO.setStatus(OrderStatusEnum.CLOSED.getValue());
            orderDO.setRefundId(result.getRefundId());
            this.updateById(orderDO);
        }
        log.info("账单更新成功");
    }

    /**
     * 订单验价，进入结算页面的订单总价和当前所有商品的总价是否一致
     *
     * @param orderTotalAmount 订单总金额
     * @param orderItems       订单商品明细
     * @return true：订单总价和商品总价一致；false：订单总价和商品总价不一致。
     */
    private boolean checkOrderPrice(Long orderTotalAmount, List<OrderItemDTO> orderItems) {
        CheckPriceDTO checkPriceDTO = new CheckPriceDTO();
        List<CheckPriceDTO.CheckSku> checkSkus = orderItems.stream().map(orderFormItem -> {
            CheckPriceDTO.CheckSku checkSku = new CheckPriceDTO.CheckSku();
            checkSku.setSkuId(orderFormItem.getSkuId());
            checkSku.setCount(orderFormItem.getCount());
            return checkSku;
        }).collect(Collectors.toList());

        checkPriceDTO.setOrderTotalAmount(orderTotalAmount); // 订单总金额
        checkPriceDTO.setCheckSkus(checkSkus); // 订单的商品明细

        // 调用验价接口，比较订单总金额和商品明细总金额，不一致则说明商品价格变动
        Result<Boolean> checkPriceResult = skuFeignClient.checkPrice(checkPriceDTO);

        boolean result = Result.isSuccess(checkPriceResult) && Boolean.TRUE.equals(checkPriceResult.getData());
        return result;
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
            SkuInfoDTO skuInfoDTO = skuFeignClient.getSkuInfo(skuId).getData();
            OrderItemDTO orderItemDTO = new OrderItemDTO();
            BeanUtil.copyProperties(skuInfoDTO, orderItemDTO);

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

    /**
     * 锁定商品库存
     *
     * @param orderToken
     * @param orderItems
     */
    private void lockStock(String orderToken, List<OrderItemDTO> orderItems) {
        LockStockDTO lockStockDTO = new LockStockDTO();
        lockStockDTO.setOrderToken(orderToken);

        List<LockStockDTO.LockedSku> lockedSkuList = orderItems.stream()
                .map(orderItem -> new LockStockDTO
                        .LockedSku()
                        .setSkuId(orderItem.getSkuId())
                        .setCount(orderItem.getCount()))
                .collect(Collectors.toList());

        lockStockDTO.setLockedSkuList(lockedSkuList);
        skuFeignClient.lockStock(lockStockDTO);
    }


    /**
     * 「实验室」订单支付
     * <p>
     * 非商城业务
     *
     * @param orderDTO
     * @return
     */
    @Override
    public Boolean payOrder(Long orderId, SeataOrderDTO orderDTO) {

        Long memberId = orderDTO.getMemberId();
        Long amount = orderDTO.getAmount();

        // 扣减账户余额
        memberFeignClient.deductBalance(memberId, amount);

        // 是否开启异常
        Boolean openEx = orderDTO.getOpenEx();
        if (openEx) {
            int i = 1 / 0;
        }

        // 修改订单【已支付】
        String orderSn = businessSnGenerator.generateSerialNo();

        boolean result = this.update(new LambdaUpdateWrapper<OmsOrder>()
                .eq(OmsOrder::getId, orderId)
                .set(OmsOrder::getOrderSn, orderSn)
                .set(OmsOrder::getStatus, OrderStatusEnum.WAIT_SHIPPING.getValue())
        );

        return result;
    }

}
