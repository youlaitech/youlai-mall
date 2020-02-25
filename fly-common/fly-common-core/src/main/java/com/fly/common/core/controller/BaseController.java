package com.fly.common.core.controller;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class BaseController<T> {

    public Page<T> initPage() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Integer pageNum = Convert.convert(Integer.class, request.getParameter("pageNum"), 1);
        Integer pageSize = Convert.convert(Integer.class, request.getParameter("pageSize"), 10);
        return new Page<>(pageNum, pageSize);
    }
}
