package com.fly.cloud.admin.service;

import com.fly.cloud.admin.service.fallback.SysUserServiceFallbackFactory;
import com.fly.common.pojo.entity.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "fly-cloud-provider-admin",fallbackFactory = SysUserServiceFallbackFactory.class)
public interface ISysUserService {
    @GetMapping("/system/user/{id}")
    SysUser get(@PathVariable(value = "id") Long id);
}
