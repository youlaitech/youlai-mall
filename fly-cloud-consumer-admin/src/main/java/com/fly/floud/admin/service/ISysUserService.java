package com.fly.floud.admin.service;

import com.fly.common.pojo.entity.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "fly-cloud-provider-admin")
public interface ISysUserService {

    @GetMapping("/system/user/{id}")
    SysUser get(@PathVariable(value = "id") Long id);
}
