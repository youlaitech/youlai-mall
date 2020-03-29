package com.fly4j.common.core.domain;

import lombok.Getter;

@Getter
public class Result<T> {
    public static final String SUCCESS_MSG = "操作成功。";
    public static final String ERROR_MSG = "操作失败！";

    /**
     * 状态类型
     */
    public enum Status {
        // 成功
        SUCCESS(0),

        // 错误
        ERROR(500);

        private final int code;

        Status(int code) {
            this.code = code;
        }

        public int value() {
            return this.code;
        }
    }

    // 状态码
    private int code;

    // 提示
    private String msg;

    // 数据
    private T data;

    private Result(){
    }

    private Result(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    private Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return new Result<>(Status.SUCCESS.code, SUCCESS_MSG);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(Status.SUCCESS.code, SUCCESS_MSG, data);
    }

    public static <T> Result<T> error() {
        return new Result<>(Status.ERROR.code, ERROR_MSG);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(Status.ERROR.code, msg);
    }

    public static <T> Result<T> error(int code,String msg){
        return new Result<>(code,msg);
    }

    public static <T> Result<T> status(boolean status){
        if(status){
            return success();
        }else{
            return error();
        }
    }

}
