package com.youlai.auth.exception;

import com.youlai.common.core.result.Result;
import com.youlai.common.core.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class AuthExceptionHandler {


    /**
     * 客户端信息错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public Result handleUsernameNotFoundException(UsernameNotFoundException e) {
        return Result.error(e.getMessage());
    }

    /**
     * 用户名和密码异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(InvalidGrantException.class)
    public Result handleInvalidGrantException(InvalidGrantException e) {
        return Result.error(e.getMessage());
    }


    /**
     * 账户异常(禁用、锁定、过期)
     *
     * @param e
     * @return
     */
    @ExceptionHandler({InternalAuthenticationServiceException.class})
    public Result handleInternalAuthenticationServiceException(InternalAuthenticationServiceException e) {
        return Result.error(e.getMessage());
    }


    @ExceptionHandler(InvalidTokenException.class)
    public Result handleInvalidTokenException(InvalidTokenException e) {
        return Result.custom(ResultCode.TOKEN_INVALID_OR_EXPIRED);
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        return Result.error(e.getMessage());
    }
}
