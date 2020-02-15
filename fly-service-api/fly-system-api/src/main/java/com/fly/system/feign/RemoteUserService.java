package com.fly.system.feign;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fly.common.core.constant.ServiceNameConstants;
import com.fly.system.feign.fallback.RemoteUserFallbackFactory;
import com.fly.system.pojo.dto.Result;
import com.fly.system.pojo.entity.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteUserService {

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
