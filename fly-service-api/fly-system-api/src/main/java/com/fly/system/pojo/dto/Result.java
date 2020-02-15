package com.fly.system.pojo.dto;

import com.fly.common.core.constant.CommonConstants;
import lombok.*;

import java.io.Serializable;

@Builder
@ToString
@AllArgsConstructor
public class Result<T> implements Serializable {

    @Getter
    @Setter
    private int code = CommonConstants.SUCCESS;

    @Getter
    @Setter
    private Object msg = "success";

    @Getter
    @Setter
    private T data;

    public Result() {
        super();
    }

    public Result(T data) {
        super();
        this.data = data;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
/*

    public Result(CommonEnums enums) {
        super();
        this.code = enums.getCode();
        this.msg = enums.getMsg();
    }
*/

    public Result(Throwable e) {
        super();
        this.code = CommonConstants.ERROR;
        this.msg = e.getMessage();
    }


    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result success( ) {
        return new Result<>(CommonConstants.SUCCESS, "success");
    }

    public static Result success(Object data) {
        return new Result<>(CommonConstants.SUCCESS, "success", data);
    }

    public static Result fail() {
        return new Result<>(CommonConstants.ERROR, "error");
    }

    public static Result status(boolean status) {
        if (status) {
            return new Result<>(CommonConstants.SUCCESS, "success", null);
        } else {
            return new Result<>(CommonConstants.ERROR, "error");
        }
    }

}