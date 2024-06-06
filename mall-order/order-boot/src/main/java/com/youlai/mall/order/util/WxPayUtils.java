package com.youlai.mall.order.util;

import com.github.binarywang.wxpay.bean.notify.SignatureHeader;
import org.springframework.http.HttpHeaders;

/**
 * 微信支付工具类
 *
 * @author Rya Hao
 * @since 2024/6/6
 */
public class WxPayUtils {

    private WxPayUtils() {
        // 私有构造方法，防止实例化
    }

    /**
     * 从 HttpHeaders 中提取微信支付签名相关的头信息，并封装为 SignatureHeader 对象。
     *
     * @param headers 包含微信支付签名头信息的 HttpHeaders 对象
     * @return SignatureHeader 对象
     */
    public static SignatureHeader getSignatureHeader(HttpHeaders headers) {
        SignatureHeader signatureHeader = new SignatureHeader();
        signatureHeader.setSignature(headers.getFirst("Wechatpay-Signature"));
        signatureHeader.setSerial(headers.getFirst("Wechatpay-Serial"));
        signatureHeader.setTimeStamp(headers.getFirst("Wechatpay-Timestamp"));
        signatureHeader.setNonce(headers.getFirst("Wechatpay-Nonce"));
        return signatureHeader;
    }
}
