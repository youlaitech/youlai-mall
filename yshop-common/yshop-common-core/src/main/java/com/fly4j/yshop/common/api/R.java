package com.fly4j.yshop.common.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fly4j.yshop.common.enums.ApiErrorCode;
import lombok.Data;

import java.io.Serializable;

@Data
public class R<T> implements Serializable {

    private long code;

    private T data;

    private String msg;

    public static <T> R<T> ok() {
        return ok(null);
    }

    public static <T> R<T> ok(T data) {
        ApiErrorCode aec = ApiErrorCode.SUCCESS;
        if (data instanceof Boolean && Boolean.FALSE.equals(data)) {
            aec = ApiErrorCode.FAILED;
        }
        return restResult(data, aec);
    }

    public static <T> R<T> failed() {
        return restResult((T) null, ApiErrorCode.FAILED.getCode(), ApiErrorCode.FAILED.getMsg());
    }

    public static <T> R<T> failed(String msg) {
        return restResult((T) null, ApiErrorCode.FAILED.getCode(), msg);
    }

    public static <T> R<T> failed(IErrorCode errorCode) {
        return restResult((T) null, errorCode.getCode(), errorCode.getMsg());
    }

    public static <T> R<T> restResult(T data, IErrorCode errorCode) {
        return restResult(data, errorCode.getCode(), errorCode.getMsg());
    }

    public static <T> R<T> restResult(T data, int code, String msg) {
        R<T> r = new R();
        r.setData(data);
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }
}
