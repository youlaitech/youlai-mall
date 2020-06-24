package com.youlai.common.exception;

import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * @author hxrui
 * @date 2020-06-24
 **/

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public Result handleException(CustomException e) {
        log.error(">> 运行时异常:{}", e.getMessage());
        return Result.error(e.getResultCode());
    }

    @ExceptionHandler(RuntimeException.class)
    public Result handleException(RuntimeException e) {
        log.error(">> 运行时异常:{}", e.getMessage());
        return Result.error(ResultCodeEnum.SYSTEM_EXECUTION_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error(">> 系统异常，请联系系统管理员,{}", e.getMessage());
        return  Result.error(ResultCodeEnum.SYSTEM_EXECUTION_ERROR);
    }
}
