package com.fly4j.yshop.oms.config;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Created by XianRui on 2020-03-03 9:22
 * feign拦截添加原request请求头和参数
 **/
@Configuration
public class FeignClientConfig implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) return;
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                Enumeration<String> values = request.getHeaders(name);
                while (values.hasMoreElements()) {
                    String value = values.nextElement();
                    requestTemplate.header(name, value);
                }
            }
        }

       /* Enumeration<String> bodyNames = request.getParameterNames();
        StringBuffer sb = new StringBuffer();
        if (bodyNames != null) {
            while (bodyNames.hasMoreElements()) {
                String name = bodyNames.nextElement();
                String values = request.getParameter(name);
                sb.append(name).append("=").append(values).append("&");
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
            requestTemplate.body(sb.toString());
        }*/
    }
}
