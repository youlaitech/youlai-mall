package com.fly4j.yshop.common.result;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author haoxr
 * @date 2020-06-23
 **/
@AllArgsConstructor
@NoArgsConstructor
public enum ResultCodeEnum implements  IResultCode, Serializable {

    SUCCESS("00000","成功"),
    CLIENT_ERROR("A0001","用户端错误"),
    REQUEST_PARAM_ERROR("A0400","用户请求参数错误"),
    REQUEST_PARAM_IS_BLANK("A0410","请求必填参数为空"),;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    private String code;

    private String msg;
}
