package com.youlai.common.web.log;

import cn.hutool.json.JSONUtil;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @Author haoxr
 * @Date 2021-03-01 16:47
 * @Version 1.0.0
 */
@Aspect
@Component
@AllArgsConstructor
@Slf4j
public class LogAspect {

    private RedisTemplate redisTemplate;

    @Pointcut("execution(public * com.youlai..*.controller.*.*(..))")
    public void Log() {
    }

    @Around("Log()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("日志记录");
        long startTime = System.nanoTime();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Log log = new Log();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method.isAnnotationPresent(ApiOperation.class)) {
            ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
            log.setDescription(apiOperation.value());
        }
        long endTime = System.nanoTime();
        log.setIntervalTime((int) (endTime - startTime));
        log.setIp(request.getRemoteUser());
        log.setUrl(request.getRequestURL().toString());
        log.setMethod(request.getMethod());
        redisTemplate.opsForSet().add("sys:log", JSONUtil.toJsonStr(log));
        Object result = joinPoint.proceed();
        return result;
    }
}
