package com.fly4j.yshop.common.exception;

import com.fly4j.yshop.common.api.IErrorCode;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    public IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }

    public ApiException(String message){
        super(message);
    }

    public ApiException(String message, Throwable cause){
        super(message, cause);
    }

    public ApiException(Throwable cause){
        super(cause);
    }
}
