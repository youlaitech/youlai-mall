package com.youlai.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by XianRui on 2020-02-24 18:01
 *
 * pre：可以在请求被路由之前调用  1
 * route：在路由请求时候被调用   2
 * post：在pre和route过滤器之后被调用 3
 * error：处理请求时发生错误时被调用  4
 *
 **/
@Component
@Slf4j
public class PreZuulFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        // 打印请求相关信息
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String method = request.getMethod();
        String servletPath = request.getServletPath();
        log.info(">>> 请求方法{}，请求路径：{}", method, servletPath);
        return null;
    }
}
