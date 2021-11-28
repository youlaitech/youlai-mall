package com.youlai.admin.component.handler;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import jodd.net.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author haoxr
 * @description 自定义流控异常
 * @createTime 2021/4/12 22:57
 */
@Component
public class DefaultBlockExceptionHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        response.setStatus(HttpStatus.ok().status());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        ObjectMapper objectMapper = new ObjectMapper();

        if(e instanceof FlowException){
            // objectMapper.writeValue 用于将java对象转位JSON格式返回调用方
            objectMapper.writeValue(response.getWriter(), Result.failed(ResultCode.FLOW_LIMITING));
        }else if(e instanceof DegradeException){
            objectMapper.writeValue(response.getWriter(),Result.failed(ResultCode.DEGRADATION));
        }
    }
}
