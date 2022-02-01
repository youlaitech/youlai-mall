package com.youlai.mall.oms.pojo.vo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Gadfly
 * @since 2021-06-04 15:18
 */
@Data
@Accessors(chain = true)
public class WxPayResponseVO {
    private String code;

    private String message;
}
