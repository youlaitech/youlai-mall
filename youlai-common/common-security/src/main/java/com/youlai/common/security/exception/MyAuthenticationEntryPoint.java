package com.youlai.common.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 自定义 token 无效异常
 *
 * @author haoxr
 * @date 2022/11/13
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        response.setContentType("application/json");

        int status = response.getStatus();
        ObjectMapper mapper = new ObjectMapper();
        if (HttpServletResponse.SC_NOT_FOUND == status) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            mapper.writeValue(response.getOutputStream(), Result.failed(ResultCode.RESOURCE_NOT_FOUND));
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            mapper.writeValue(response.getOutputStream(), Result.failed(ResultCode.TOKEN_INVALID));
        }

    }
}
