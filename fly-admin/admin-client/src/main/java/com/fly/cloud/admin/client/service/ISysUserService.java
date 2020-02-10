package com.fly.cloud.admin.client.service;

import com.fly.cloud.admin.client.service.fallback.SysUserServiceFallbackFactory;
import com.fly.common.pojo.entity.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "admin-server",fallbackFactory = SysUserServiceFallbackFactory.class)
public interface ISysUserService {
    @GetMapping("/user/{id}")
    SysUser get(@PathVariable(value = "id") Long id);
}
