package com.fly.system.feign;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fly.common.constant.ServiceNameConstants;
import com.fly.common.core.domain.Result;
import com.fly.system.feign.hystrix.SysUserServiceFeignHystrix;
import com.fly.system.domain.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = SysUserServiceFeignHystrix.class)
public interface SysUserServiceFeign {

    @GetMapping("/users/page")
    Result<IPage<SysUser>> page();

    @GetMapping("/users/{id}")
    Result<SysUser> getById(@PathVariable("id") Long id);

    @PostMapping("/users")
    Result<SysUser> add(@RequestBody SysUser sysUser);

    @PutMapping("/users/${id}")
    Result<SysUser> update(@PathVariable("id") Long id,@RequestBody SysUser sysUser);

    @DeleteMapping("/users/${ids}")
    Result<Boolean> delete(@PathVariable Long[] ids);

}
