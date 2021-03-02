package com.youlai.common.web.aspect;

import cn.hutool.json.JSONUtil;
import com.youlai.common.web.pojo.domain.LoginLog;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @Author HXR
 * @CreateTime 2021-03-01 16:47
 */
@Aspect
@Component
@AllArgsConstructor
@Slf4j
public class LoginLogAspect {

    private RedisTemplate redisTemplate;

    @Pointcut("execution(public * com.youlai..*.controller.*.*(..))")
    public void Log() {
    }

    @Around("Log()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("登录日志记录");
        long startTime = System.nanoTime();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        LoginLog loginLog = new LoginLog();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method.isAnnotationPresent(ApiOperation.class)) {
            ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
            loginLog.setDescription(apiOperation.value());
        }
        long endTime = System.nanoTime();
        loginLog.setElapsedTime((int) (endTime - startTime));
        loginLog.setIp(request.getRemoteUser());
        loginLog.setUrl(request.getRequestURL().toString());
        loginLog.setMethod(request.getMethod());
        redisTemplate.opsForSet().add("log:login", JSONUtil.toJsonStr(loginLog));
        Object result = joinPoint.proceed();
        return result;
    }
}
