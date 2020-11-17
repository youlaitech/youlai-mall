package com.youlai.auth.exception;

import com.youlai.common.core.result.Result;
import com.youlai.common.core.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class AuthExceptionHandler {

    @ExceptionHandler(InvalidTokenException.class)
    public Result handleBizException(InvalidTokenException e) {
        return Result.custom(ResultCode.TOKEN_INVALID_OR_EXPIRED);
    }
}
