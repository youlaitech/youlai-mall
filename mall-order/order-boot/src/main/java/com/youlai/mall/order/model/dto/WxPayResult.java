package com.youlai.mall.order.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 微信支付回调结果类，用于封装微信支付回调处理后的响应结果。
 *
 * <p>该类包含两个属性：</p>
 * <ul>
 *   <li>{@code code}：表示处理结果的状态码，通常为 "SUCCESS" 或 "FAIL"。</li>
 *   <li>{@code message}：表示处理结果的描述信息。</li>
 * </ul>
 *
 * <p>示例用法：</p>
 * <pre>
 *     WxPayResult result = new WxPayResult()
 *         .setCode("SUCCESS")
 *         .setMessage("处理成功");
 * </pre>

 * @author Ray
 * @since 2021-06-04
 */
@Data
@Accessors(chain = true)
public class WxPayResult {

    /**
     * 处理结果的状态码。
     * 通常为 "SUCCESS" 或 "FAIL"。
     */
    private String code;

    /**
     * 处理结果的描述信息。
     */
    private String message;
}
