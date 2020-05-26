package com.fly4j.yshop.common.api;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    private long code;

    private T data;

    private String msg;

    public static <T> Result<T> ok() {
        return ok(null);
    }

    public static <T> Result<T> ok(T data) {
        ApiErrorCode aec = ApiErrorCode.SUCCESS;
        if (data instanceof Boolean && Boolean.FALSE.equals(data)) {
            aec = ApiErrorCode.FAILED;
        }
        return restResult(data, aec);
    }

    public static <T> Result<T> failed() {
        return restResult((T) null, ApiErrorCode.FAILED.getCode(), ApiErrorCode.FAILED.getMsg());
    }

    public static <T> Result<T> failed(String msg) {
        return restResult((T) null, ApiErrorCode.FAILED.getCode(), msg);
    }

    public static <T> Result<T> failed(IErrorCode errorCode) {
        return restResult((T) null, errorCode.getCode(), errorCode.getMsg());
    }

    public static <T> Result<T> restResult(T data, IErrorCode errorCode) {
        return restResult(data, errorCode.getCode(), errorCode.getMsg());
    }

    public static <T> Result<T> restResult(T data, int code, String msg) {
        Result<T> r = new Result();
        r.setData(data);
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static <T> Result<T> status(boolean status) {
        return status ? ok() : failed();
    }
}
