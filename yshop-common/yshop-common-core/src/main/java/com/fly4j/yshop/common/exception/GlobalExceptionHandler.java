package com.fly4j.yshop.common.exception;

<<<<<<< HEAD
import com.baomidou.mybatisplus.extension.api.IErrorCode;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.enums.ApiErrorCode;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
=======
import com.fly4j.yshop.common.api.IErrorCode;
import com.fly4j.yshop.common.api.R;
import com.fly4j.yshop.common.enums.ApiErrorCode;
>>>>>>> 3d26641c4a5a111308766367225743ff672555c3
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
<<<<<<< HEAD
            public long getCode() {
=======
            public int getCode() {
>>>>>>> 3d26641c4a5a111308766367225743ff672555c3
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
<<<<<<< HEAD
            public long getCode() {
=======
            public int getCode() {
>>>>>>> 3d26641c4a5a111308766367225743ff672555c3
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
<<<<<<< HEAD
            public long getCode() {
=======
            public int getCode() {
>>>>>>> 3d26641c4a5a111308766367225743ff672555c3
                return 500;
            }

            @Override
            public String getMsg() {
                return "系统异常:" + e.getMessage();
            }
        });
    }

}
