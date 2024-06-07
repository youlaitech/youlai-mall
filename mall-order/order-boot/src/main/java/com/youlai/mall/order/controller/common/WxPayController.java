package com.youlai.mall.order.controller.common;

import com.github.binarywang.wxpay.bean.notify.SignatureHeader;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.youlai.mall.order.model.result.WxPayResult;
import com.youlai.mall.order.service.app.OrderService;
import com.youlai.mall.order.util.WxPayUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 微信支付 APIv3
 *
 * @author Gadfly
 * @since 2021-05-27 14:24
 */
@Tag(name = "微信支付接口-APIv3")
@RestController
@RequiredArgsConstructor
@RequestMapping("/common-api/v3/wx-pay")
@Validated
@Slf4j
public class WxPayController {

    private final OrderService orderService;

    @Operation(summary = "微信下单支付结果回调")
    @PostMapping("/notify/order")
    public WxPayResult wxPayOrderNotify(
            @RequestBody String notifyData,
            @RequestHeader HttpHeaders headers
    ) throws WxPayException {
        SignatureHeader signatureHeader = WxPayUtils.getSignatureHeader(headers);
        try {
            orderService.handleWxPayOrderNotify(signatureHeader, notifyData);
            return new WxPayResult()
                    .setCode(WxPayConstants.ResultCode.SUCCESS)
                    .setMessage("成功");
        } catch (Exception e) {
            return new WxPayResult()
                    .setCode(WxPayConstants.ResultCode.FAIL)
                    .setMessage("失败");
        }

    }

    @Operation(summary = "微信退款结果回调")
    @PostMapping("/notify/refund")
    public WxPayResult wxPayRefundNotify(
            @Parameter(description = "加密数据") @RequestBody String notifyData,
            @Parameter(description = "请求头") @RequestHeader HttpHeaders headers
    ) {
        SignatureHeader signatureHeader = WxPayUtils.getSignatureHeader(headers);
        try {
            orderService.handleWxPayRefundNotify(signatureHeader, notifyData);
            return new WxPayResult()
                    .setCode(WxPayConstants.ResultCode.SUCCESS)
                    .setMessage("成功");
        } catch (Exception e) {
            return new WxPayResult()
                    .setCode(WxPayConstants.ResultCode.FAIL)
                    .setMessage("失败");

        }
    }
}