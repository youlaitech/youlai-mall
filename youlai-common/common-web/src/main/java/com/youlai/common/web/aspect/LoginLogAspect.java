package com.youlai.common.web.aspect;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import com.youlai.common.web.pojo.domain.LoginLog;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hxr
 * @date 2021-03-01
 */
@Aspect
@Component
@AllArgsConstructor
@Slf4j
@ConditionalOnProperty(value = "spring.application.name",havingValue = "youlai-auth")
public class LoginLogAspect {

    @Pointcut("execution(public * com.youlai.auth.controller.AuthController.postAccessToken(..))")
    public void Log() {
    }

    @Around("Log()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 时间统计
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime =  System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        // 获取方法签名
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        String description = signature.getMethod().getAnnotation(ApiOperation.class).value();

        // 获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String requestIp= ServletUtil.getClientIP(request);
        String requestUrl=request.getRequestURL().toString();
        String requestMethod=request.getMethod();

        LoginLog loginLog = new LoginLog();
        loginLog.setStartTime(startTime);
        loginLog.setElapsedTime(elapsedTime);
        loginLog.setDescription(description );
        loginLog.setRequestIp(requestIp);
        loginLog.setRequestUrl(requestUrl);
        loginLog.setRequestMethod(requestMethod);
        loginLog.setResult(result);
        log.info(JSONUtil.toJsonStr(loginLog));
        return result;
    }
}
