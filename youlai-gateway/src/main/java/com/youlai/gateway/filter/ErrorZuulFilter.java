package com.youlai.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * Created by XianRui on 2020-03-02 11:22
 * pre：可以在请求被路由之前调用  1
 * route：在路由请求时候被调用  2
 * post：在pre和route过滤器之后被调用 3
 * error：处理请求时发生错误时被调用  4
 **/
@Component
@Slf4j
public class ErrorZuulFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return 4;
    }

    /**
     * 是否需要执行该过滤器 true是需要
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info(">>> 异常过滤器");
        RequestContext ctx = RequestContext.getCurrentContext();
        log.error(ctx.getResponseBody());
        return null;
    }
}
