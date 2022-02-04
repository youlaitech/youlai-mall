package com.youlai.mall.oms.controller.app;

import com.github.binarywang.wxpay.bean.notify.SignatureHeader;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.youlai.mall.oms.pojo.vo.WxPayResponseVO;
import com.youlai.mall.oms.service.IOrderService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 微信回调接口
 *
 * @author Gadfly
 * @since 2021-05-27 14:24
 */
@Api(tags = "「移动端」微信支付回调接口")
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/callback-api/v1/wx-pay")
public class WxPayCallbackController {

    private final IOrderService orderService;

    /**
     * 微信下单支付结果回调
     *
     * @param notifyData 加密数据
     * @param headers    请求头
     * @return {"code": "SUCCESS", "message": "成功"}
     */
    @PostMapping("/notify-order-v3")
    public WxPayResponseVO wxPayOrderNotify(@RequestBody String notifyData,
                                            @RequestHeader HttpHeaders headers) throws WxPayException {
        SignatureHeader signatureHeader = getSignatureHeaderByHttpHeaders(headers);
        orderService.handleWxPayOrderNotify(signatureHeader, notifyData);
        return new WxPayResponseVO()
                .setCode(WxPayConstants.ResultCode.SUCCESS)
                .setMessage("成功");
    }

    /**
     * 微信退款结果回调
     *
     * @param notifyData 加密数据
     * @param headers    请求头
     * @return {"code": "SUCCESS", "message": "成功"}
     */
    @PostMapping("/notify-refund-v3")
    public WxPayResponseVO wxPayRefundNotify(@RequestBody String notifyData,
                                             @RequestHeader HttpHeaders headers) throws WxPayException {
        SignatureHeader signatureHeader = getSignatureHeaderByHttpHeaders(headers);
        orderService.handleWxPayRefundNotify(signatureHeader, notifyData);
        return new WxPayResponseVO()
                .setCode(WxPayConstants.ResultCode.SUCCESS)
                .setMessage("成功");
    }

    private SignatureHeader getSignatureHeaderByHttpHeaders(HttpHeaders headers) {
        SignatureHeader signatureHeader = new SignatureHeader();
        signatureHeader.setSignature(headers.getFirst("Wechatpay-Signature"));
        signatureHeader.setSerial(headers.getFirst("Wechatpay-Serial"));
        signatureHeader.setTimeStamp(headers.getFirst("Wechatpay-Timestamp"));
        signatureHeader.setNonce(headers.getFirst("Wechatpay-Nonce"));
        return signatureHeader;
    }
}
