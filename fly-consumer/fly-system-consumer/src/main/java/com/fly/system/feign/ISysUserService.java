package com.fly.system.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly.common.constant.ServiceNameConstants;
import com.fly.system.domain.SysUser;
import com.fly.system.feign.fallback.SysUserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = ServiceNameConstants.SYSTEM_PROVIDER,contextId ="user", fallbackFactory = SysUserServiceFallback.class)
public interface ISysUserService {

    @GetMapping("/users/page")
    Page<SysUser> page();

    @GetMapping("/users/{id}")
    SysUser getById(@PathVariable("id") Long id);

    @PostMapping("/users")
    boolean add(@RequestBody SysUser sysUser);

    @PutMapping("/users/{id}")
    boolean update(@PathVariable("id") Long id,@RequestBody SysUser sysUser);

    @DeleteMapping("/users/{ids}")
    boolean delete(@PathVariable Long[] ids);

}
