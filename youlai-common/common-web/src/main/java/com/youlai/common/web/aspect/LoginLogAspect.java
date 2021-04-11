package com.youlai.common.web.aspect;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.youlai.common.constant.AuthConstants;
import com.youlai.common.web.util.IPUtils;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author hxr
 * @date 2021-03-01
 */
/*@Aspect
@Component
@AllArgsConstructor
@Slf4j
@ConditionalOnProperty(value = "spring.application.name", havingValue = "youlai-auth")*/
public class LoginLogAspect {

    @Pointcut("execution(public * com.youlai.auth.controller.AuthController.postAccessToken(..))")
    public void Log() {
    }

    @Around("Log()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        LocalDateTime startTime = LocalDateTime.now();
        Object result = joinPoint.proceed();

        // 获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 刷新token不记录
        String grantType=request.getParameter(AuthConstants.GRANT_TYPE_KEY);
        if(grantType.equals(AuthConstants.REFRESH_TOKEN)){
            return result;
        }

        // 时间统计
        LocalDateTime endTime = LocalDateTime.now();
        long elapsedTime = Duration.between(startTime, endTime).toMillis(); // 请求耗时（毫秒）

        // 获取接口描述信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String description = signature.getMethod().getAnnotation(ApiOperation.class).value();// 方法描述

        String username = request.getParameter(AuthConstants.USER_NAME_KEY); // 登录用户名
        String date = startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // 索引名需要，因为默认生成索引的date时区不一致

        // 获取token
        String token = Strings.EMPTY;
        if (request != null) {
            JSONObject jsonObject = JSONUtil.parseObj(result);
            token = jsonObject.getStr("value");
        }

        String clientIP = IPUtils.getIpAddr(request);  // 客户端请求IP（注意：如果使用Nginx代理需配置）
        String region = IPUtils.getCityInfo(clientIP); // IP对应的城市信息

        // MDC 扩展logback字段，具体请看logback-spring.xml的自定义日志输出格式
        MDC.put("elapsedTime", StrUtil.toString(elapsedTime));
        MDC.put("description", description);
        MDC.put("region", region);
        MDC.put("username", username);
        MDC.put("date", date);
        MDC.put("token", token);
        MDC.put("clientIP", clientIP);

        //log.info("{} 登录，耗费时间 {} 毫秒", username, elapsedTime); // 收集日志这里必须打印一条日志，内容随便吧，记录在message字段，具体看logback-spring.xml文件
        return result;
    }
}
