package com.youlai.common.web.security.advice;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import com.youlai.common.constant.GlobalConstants;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import com.youlai.common.web.security.annotation.RequirePerms;
import com.youlai.common.web.util.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 权限校验切面
 *
 * TODO 整合SpEL表达式
 *
 * @author haoxr
 * @date 2022/7/30
 */
@Component
@Aspect
@RequiredArgsConstructor
@Slf4j
public class PermissionAdvice {

    private final RedisTemplate redisTemplate;

    /**
     * -
     * 权限切点定义
     */
    @Pointcut("@annotation(com.youlai.common.web.security.annotation.RequirePerms)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("permission verification start.");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        RequirePerms annotation = signature.getMethod().getAnnotation(RequirePerms.class);

        String[] requiredPerms = annotation.value(); // 需要的权限标识集合
        log.info("required perms ：{}", requiredPerms);

        // 权限校验
        boolean passFlag = false;
        List<String> userRoles = UserUtils.getRoles();

        // 超级管理员放行
        if (UserUtils.isRoot()) {
            return joinPoint.proceed();
        }

        Map<String, Object> permRolesMap = redisTemplate.opsForHash().entries(GlobalConstants.BTN_PERM_ROLES_KEY);
        if (permRolesMap != null) {
            for (String requiredPerm : requiredPerms) {
                List<String> hasPermRoles = Convert.toList(String.class, permRolesMap.get(requiredPerm)); // 拥有访问权限的角色
                if (CollectionUtil.isNotEmpty(hasPermRoles)) {
                    for (String hasPermRole : hasPermRoles) {
                        boolean hasPerm = userRoles.stream().anyMatch(userRole -> userRole.equals(hasPermRole));
                        if (hasPerm) {
                            passFlag = true;
                            break;
                        }
                    }
                }
            }
        }
        log.info("authentication result :{}", passFlag);
        if (passFlag == false) {
            return Result.failed(ResultCode.ACCESS_UNAUTHORIZED);
        }

        return joinPoint.proceed();
    }

}
