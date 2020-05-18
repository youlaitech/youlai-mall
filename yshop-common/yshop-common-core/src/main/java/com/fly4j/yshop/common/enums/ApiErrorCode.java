package com.fly4j.yshop.common.enums;

import com.fly4j.yshop.common.api.IErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiErrorCode implements IErrorCode {

    FAILED(-1,"操作失败"),
    SUCCESS(0,"执行成功");

    private int code;

    private String msg;

}
