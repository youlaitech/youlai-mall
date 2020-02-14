package com.fly.cloud.seckill.exception;

/**
 * 秒杀关闭异常
 *
 */
public class SeckillCloseException extends SeckillException {

    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}