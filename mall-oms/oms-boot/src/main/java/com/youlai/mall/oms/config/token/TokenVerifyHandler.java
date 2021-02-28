package com.youlai.mall.oms.config.token;

import com.youlai.mall.oms.service.TokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author huawei
 * @desc
 * @email huawei_code@163.com
 * @date 2021/1/21
 */
@Component
@AllArgsConstructor
@Slf4j
public class TokenVerifyHandler extends HandlerInterceptorAdapter {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod method = (HandlerMethod) handler;
        TokenVerify tokenVerify = method.getMethodAnnotation(TokenVerify.class);
        if (tokenVerify != null) {
            log.debug("请求：{} 使用 @TokenVerify 注解，被拦截器拦截", request.getRequestURI());
            // 校验token，判断是否重复提交
            tokenService.checkToken(request);
        }
        return true;
    }


}
