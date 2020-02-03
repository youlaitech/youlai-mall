package com.fly.cloud.seckill.exception;

/**
 * 秒杀相关的异常
 *
 */
public class SeckillException extends RuntimeException {

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}