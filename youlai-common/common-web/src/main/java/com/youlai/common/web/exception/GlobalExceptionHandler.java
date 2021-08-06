package com.youlai.common.web.exception;

import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.concurrent.CompletionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 全局系统异常处理
 *
 * @author hxrui
 * @date 2020-02-25 13:54
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 表单绑定到 java bean 出错时抛出 BindException 异常
     */
    @ExceptionHandler(BindException.class)
    public <T> Result<T> processException(BindException e, HttpServletResponse response) {
        log.error(e.getMessage(), e);
        JSONObject msg = new JSONObject();
        e.getAllErrors().forEach(error -> {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                msg.set(fieldError.getField(),
                        fieldError.getDefaultMessage());
            } else {
                msg.set(error.getObjectName(),
                        error.getDefaultMessage());
            }
        });
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return Result.failed(ResultCode.PARAM_ERROR, msg.toString());
    }

    /**
     * 普通参数(非 java bean)校验出错时抛出 ConstraintViolationException 异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public <T> Result<T> processException(ConstraintViolationException e, HttpServletResponse response) {
        log.error(e.getMessage(), e);
        JSONObject msg = new JSONObject();
        e.getConstraintViolations().forEach(constraintViolation -> {
            String template = constraintViolation.getMessage();
            String path = constraintViolation.getPropertyPath().toString();
            msg.set(path, template);
        });
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return Result.failed(ResultCode.PARAM_ERROR, msg.toString());
    }

    /**
     * NoHandlerFoundException
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public <T> Result<T> processException(NoHandlerFoundException e, HttpServletResponse response) {
        log.error(e.getMessage(), e);
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return Result.failed(ResultCode.RESOURCE_NOT_FOUND);
    }

    /**
     * MissingServletRequestParameterException
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public <T> Result<T> processException(MissingServletRequestParameterException e, HttpServletResponse response) {
        log.error(e.getMessage(), e);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return Result.failed(ResultCode.PARAM_IS_NULL);
    }

    /**
     * MethodArgumentTypeMismatchException
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public <T> Result<T> processException(MethodArgumentTypeMismatchException e, HttpServletResponse response) {
        log.error(e.getMessage(), e);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return Result.failed(ResultCode.PARAM_ERROR, "类型错误");
    }

    /**
     * ServletException
     */
    @ExceptionHandler(ServletException.class)
    public <T> Result<T> processException(ServletException e, HttpServletResponse response) {
        log.error(e.getMessage(), e);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return Result.failed(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public <T> Result<T> handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) {
        log.error("非法参数异常，异常原因：{}", e.getMessage(), e);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return Result.failed(e.getMessage());
    }

    @ExceptionHandler(JsonProcessingException.class)
    public <T> Result<T> handleJsonProcessingException(JsonProcessingException e, HttpServletResponse response) {
        log.error("Json转换异常，异常原因：{}", e.getMessage(), e);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return Result.failed(e.getMessage());
    }

    /**
     * HttpMessageNotReadableException
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public <T> Result<T> processException(HttpMessageNotReadableException e, HttpServletResponse response) {
        log.error(e.getMessage(), e);
        String errorMessage = "请求体不可为空";
        Throwable cause = e.getCause();
        if (cause != null) {
            errorMessage = convertMessage(cause);
        }
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return Result.failed(errorMessage);
    }

    /**
     * TypeMismatchException
     */
    @ExceptionHandler(TypeMismatchException.class)
    public <T> Result<T> processException(TypeMismatchException e, HttpServletResponse response) {
        log.error(e.getMessage(), e);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return Result.failed(e.getMessage());
    }

    /**
     * FeignException
     */
    @ExceptionHandler(FeignException.class)
    public <T> Result<T> processException(FeignException e, HttpServletResponse response) {
        log.error(e.getMessage(), e);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return Result.failed("微服务调用异常");
    }

    /**
     * CompletionException
     */
    @ExceptionHandler(CompletionException.class)
    public <T> Result<T> processException(CompletionException e, HttpServletResponse response) {
        log.error(e.getMessage(), e);
        if (e.getMessage().startsWith("feign.FeignException")) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return Result.failed("微服务调用异常");
        }
        return handleException(e, response);
    }

    @ExceptionHandler(BizException.class)
    public <T> Result<T> handleBizException(BizException e, HttpServletResponse response) {
        log.error("业务异常，异常原因：{}", e.getMessage(), e);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        if (e.getResultCode() != null) {
            return Result.failed(e.getResultCode());
        }
        return Result.failed(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public <T> Result<T> handleException(Exception e, HttpServletResponse response) {
        log.error("未知异常，异常原因：{}", e.getMessage(), e);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return Result.failed();
    }

    /**
     * 传参类型错误时，用于消息转换
     *
     * @param throwable 异常
     * @return 错误信息
     */
    private String convertMessage(Throwable throwable) {
        String error = throwable.toString();
        String regulation = "\\[\"(.*?)\"]+";
        Pattern pattern = Pattern.compile(regulation);
        Matcher matcher = pattern.matcher(error);
        String group = "";
        if (matcher.find()) {
            String matchString = matcher.group();
            matchString = matchString
                    .replace("[", "")
                    .replace("]", "");
            matchString = matchString.replaceAll("\\\"", "") + "字段类型错误";
            group += matchString;
        }
        return group;
    }
}
