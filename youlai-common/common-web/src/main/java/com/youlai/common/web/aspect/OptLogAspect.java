package com.youlai.common.web.aspect;

import cn.hutool.json.JSONUtil;
import com.youlai.common.web.pojo.domain.LoginLog;
import com.youlai.common.web.pojo.domain.OptLog;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.Markers;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author HXR
 * @CreateTime 2021-03-01 16:47
 */
@Aspect
@Component
@AllArgsConstructor
public class OptLogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(OptLogAspect.class);
    @Pointcut("execution(public * com.youlai..*.controller.*.*(..)) || execution(public * com.youlai.*.controller.*(..))")
    public void Log() {
    }

    @Around("Log()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        OptLog optLog = new OptLog();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method.isAnnotationPresent(ApiOperation.class)) {
            ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
            optLog.setDescription(apiOperation.value());
        }
        long endTime = System.nanoTime();
        optLog.setElapsedTime((int) (endTime - startTime));
        optLog.setIp(request.getRemoteUser());
        optLog.setUrl(request.getRequestURL().toString());
        optLog.setMethod(request.getMethod());
        Object result = joinPoint.proceed();
        optLog.setResult(result);

        Map<String,Object> logMap = new HashMap<>();
        logMap.put("url",optLog.getUrl());
        logMap.put("method",optLog.getMethod());
        logMap.put("elapsedTime",optLog.getElapsedTime());
        logMap.put("description",optLog.getDescription());
        logMap.put("result",result);

        LOGGER.info("123");
        LOGGER.info(Markers.appendEntries(logMap),JSONUtil.parse(optLog).toString());
        return result;
    }
}
