package com.fly4j.yshop.common.exception;

import com.fly4j.yshop.common.api.IErrorCode;
import com.fly4j.yshop.common.api.R;
import com.fly4j.yshop.common.enums.ApiErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by XianRui on 2020-02-25 16:03
 **/

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public R handleException(ApiException e) {
        log.error(">> API调用异常:{}", e.getMessage());
        return R.failed(new IErrorCode() {
            @Override
            public int getCode() {
                return ApiErrorCode.FAILED.getCode();
            }

            @Override
            public String getMsg() {
                return "API调用异常:" + e.getMessage();
            }
        });
    }

    @ExceptionHandler(RuntimeException.class)
    public R handleException(RuntimeException e) {
        log.error(">> 运行时异常:{}", e.getMessage());
        return R.failed(new IErrorCode() {
            @Override
            public int getCode() {
                return ApiErrorCode.FAILED.getCode();
            }

            @Override
            public String getMsg() {
                return "运行时异常:" + e.getMessage();
            }
        });
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        log.error(">> 系统异常，请联系系统管理员,{}", e.getMessage());
        return R.failed(new IErrorCode() {
            @Override
            public int getCode() {
                return 500;
            }

            @Override
            public String getMsg() {
                return "系统异常:" + e.getMessage();
            }
        });
    }

}
