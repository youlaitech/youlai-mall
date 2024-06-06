package com.youlai.mall.order.model.result;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 *
 * @author Ray Hao
 * @since 2021-06-04
 */
@Data
@Accessors(chain = true)
public class WxPayResult {

    private String code;

    private String message;
}
