package com.youlai.common.web.exception;

import com.youlai.common.result.IResultCode;
import lombok.Getter;

/**
 * 自定义业务异常
 *
 * @author haoxr
 * @date 2022/7/31
 */
@Getter
public class ApiException extends RuntimeException {

    public IResultCode resultCode;

    public ApiException(IResultCode errorCode) {
        super(errorCode.getMsg());
        this.resultCode = errorCode;
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
