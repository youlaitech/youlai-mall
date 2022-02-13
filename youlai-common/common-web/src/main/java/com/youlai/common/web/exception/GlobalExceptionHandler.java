package com.youlai.common.web.exception;

import cn.hutool.core.util.StrUtil;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.sql.SQLSyntaxErrorException;
import java.util.concurrent.CompletionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 全局系统异常处理
 * 调整异常处理的HTTP状态码，丰富异常处理类型
 *
 * @author hxrui
 * @author Gadfly
 * @date 2020-02-25 13:54
 * <p>
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 表单绑定到 java bean 出错时抛出 BindException 异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public <T> Result<T> processException(BindException e) {
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
        return Result.failed(ResultCode.PARAM_ERROR, msg.toString());
    }

    /**
     * 普通参数(非 java bean)校验出错时抛出 ConstraintViolationException 异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public <T> Result<T> processException(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        JSONObject msg = new JSONObject();
        e.getConstraintViolations().forEach(constraintViolation -> {
            String template = constraintViolation.getMessage();
            String path = constraintViolation.getPropertyPath().toString();
            msg.set(path, template);
        });
        return Result.failed(ResultCode.PARAM_ERROR, msg.toString());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public <T> Result<T> processException(ValidationException e) {
        log.error(e.getMessage(), e);
        return Result.failed(ResultCode.PARAM_ERROR, "参数校验失败");
    }

    /**
     * NoHandlerFoundException
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public <T> Result<T> processException(NoHandlerFoundException e) {
        log.error(e.getMessage(), e);
        return Result.failed(ResultCode.RESOURCE_NOT_FOUND);
    }

    /**
     * MissingServletRequestParameterException
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public <T> Result<T> processException(MissingServletRequestParameterException e) {
        log.error(e.getMessage(), e);
        return Result.failed(ResultCode.PARAM_IS_NULL);
    }

    /**
     * MethodArgumentTypeMismatchException
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public <T> Result<T> processException(MethodArgumentTypeMismatchException e) {
        log.error(e.getMessage(), e);
        return Result.failed(ResultCode.PARAM_ERROR, "类型错误");
    }

    /**
     * ServletException
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServletException.class)
    public <T> Result<T> processException(ServletException e) {
        log.error(e.getMessage(), e);
        return Result.failed(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public <T> Result<T> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("非法参数异常，异常原因：{}", e.getMessage(), e);
        return Result.failed(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JsonProcessingException.class)
    public <T> Result<T> handleJsonProcessingException(JsonProcessingException e) {
        log.error("Json转换异常，异常原因：{}", e.getMessage(), e);
        return Result.failed(e.getMessage());
    }

    /**
     * HttpMessageNotReadableException
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public <T> Result<T> processException(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        String errorMessage = "请求体不可为空";
        Throwable cause = e.getCause();
        if (cause != null) {
            errorMessage = convertMessage(cause);
        }
        return Result.failed(errorMessage);
    }

    /**
     * TypeMismatchException
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TypeMismatchException.class)
    public <T> Result<T> processException(TypeMismatchException e) {
        log.error(e.getMessage(), e);
        return Result.failed(e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(SQLSyntaxErrorException.class)
    public <T> Result<T> processSQLSyntaxErrorException(SQLSyntaxErrorException e) {
        log.error(e.getMessage(), e);
        String errorMsg = e.getMessage();
        if (StrUtil.isNotBlank(errorMsg) && errorMsg.contains("denied to user")) {
            return Result.failed("数据库用户无操作权限，建议本地搭建数据库环境");
        } else {
            return Result.failed(e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CompletionException.class)
    public <T> Result<T> processException(CompletionException e) {
        if (e.getMessage().startsWith("feign.FeignException")) {
            return Result.failed("微服务调用异常");
        }
        return handleException(e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FeignException.BadRequest.class)
    public <T> Result<T> processException(FeignException.BadRequest e) {
        log.info("微服务feign调用异常:{}", e.getMessage());
        return Result.failed(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BizException.class)
    public <T> Result<T> handleBizException(BizException e) {
        log.error("业务异常，异常原因：{}", e.getMessage(), e);
        if (e.getResultCode() != null) {
            return Result.failed(e.getResultCode());
        }
        return Result.failed(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public <T> Result<T> handleException(Exception e) {
        return Result.failed(e.getLocalizedMessage());
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
