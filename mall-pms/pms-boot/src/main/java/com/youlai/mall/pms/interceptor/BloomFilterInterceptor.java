package com.youlai.mall.pms.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youlai.common.result.Result;
import com.youlai.mall.pms.common.constant.PmsConstants;
import com.youlai.mall.pms.component.BloomRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;


/**
 * @author DaniR
 * @version 1.0
 * @description 布隆过滤器拦截器
 * @createDate 2021/6/23 20:50
 */
@Slf4j
public class BloomFilterInterceptor implements HandlerInterceptor {

    @Autowired
    private BloomRedisService bloomRedisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String currentUrl = request.getRequestURI();
        PathMatcher matcher = new AntPathMatcher();
        Map<String, String> pathVariable;
        try {
            pathVariable = matcher.extractUriTemplateVariables("/app-api/v1/goods/{id}", currentUrl);
        } catch (IllegalStateException e) {
            // 路径不匹配则放行
            return true;
        }
        if (bloomRedisService.includeByBloomFilter(PmsConstants.GOODS_BLOOM_FILTER, pathVariable.get("id"))) {
            return true;
        }

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String result = new ObjectMapper().writeValueAsString(Result.failed("商品不存在!"));
        response.getWriter().print(result);
        return false;
    }
}
