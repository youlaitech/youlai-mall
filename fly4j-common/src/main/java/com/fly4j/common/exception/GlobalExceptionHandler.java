package com.fly4j.common.exception;

import com.fly4j.common.core.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by XianRui on 2020-02-25 16:03
 **/

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public Result handleException(RuntimeException e) {
        log.error(">> 运行时异常:{}",e.getMessage());
        return Result.error("运行时异常：" + e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error(">> 系统异常，请联系系统管理员,{}",e.getMessage());
        return Result.error("系统异常：" + e.getMessage());
    }

}
