package com.youlai.mall.order.service.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.binarywang.wxpay.bean.notify.SignatureHeader;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.youlai.mall.order.model.entity.OmsOrder;
import com.youlai.mall.order.model.form.OrderPayForm;
import com.youlai.mall.order.model.form.OrderSubmitForm;
import com.youlai.mall.order.model.query.OrderPageQuery;
import com.youlai.mall.order.model.vo.OrderConfirmVO;
import com.youlai.mall.order.model.vo.OrderPageVO;

/**
 * 订单业务接口
 *
 * @author huawei
 * @since 2020-12-30 22:31:10
 */
public interface OrderService extends IService<OmsOrder> {

    /**
     * 订单确认 → 进入创建订单页面
     * <p>
     * 获取购买商品明细、用户默认收货地址、防重提交唯一token
     * 进入订单创建页面有两个入口，1：立即购买；2：购物车结算
     *
     * @param skuId 直接购买必填，购物车结算不填
     * @return {@link OrderConfirmVO}
     */
    OrderConfirmVO confirmOrder(Long skuId);

    /**
     * 订单提交
     *
     * @param orderSubmitForm {@link OrderSubmitForm}
     * @return 订单编号
     */
    String submitOrder(OrderSubmitForm orderSubmitForm);

    /**
     * 订单支付
     */
    <T> T payOrder(OrderPayForm paymentForm);

    /**
     * 系统关闭订单
     */
    boolean closeOrder(String orderSn);

    /**
     * 删除订单
     */
    boolean deleteOrder(Long id);

    /**
     * 处理微信支付成功回调
     *
     * @param signatureHeader 签名头
     * @param notifyData      加密通知
     * @throws WxPayException 微信异常
     */
    void handleWxPayOrderNotify(SignatureHeader signatureHeader, String notifyData) throws WxPayException;

    /**
     * 处理微信退款成功回调
     *
     * @param signatureHeader 签名头
     * @param notifyData      加密通知
     * @throws WxPayException 微信异常
     */
    void handleWxPayRefundNotify(SignatureHeader signatureHeader, String notifyData) throws WxPayException;

    /**
     * 订单分页列表
     *
     * @param queryParams 订单分页查询参数
     * @return {@link OrderPageVO}
     */
    IPage<OrderPageVO> getOrderPage(OrderPageQuery queryParams);

}

