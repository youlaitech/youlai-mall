package com.youlai.auth.component;


import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youlai.common.core.result.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint  implements AuthenticationEntryPoint {


    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setStatus(HttpStatus.HTTP_OK);
        Result result = Result.error(e.getMessage());
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        response.getWriter().print(objectMapper.writeValueAsString(result));
        response.getWriter().flush();
    }
}
