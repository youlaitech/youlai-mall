package com.fly.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly.common.core.domain.Result;
import com.fly.system.domain.SysUser;
import com.fly.system.feign.ISysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by XianRui on 2020-03-02 20:58
 **/
@RestController
@RequestMapping("/users")
public class SysUserController {
    @Resource
    private ISysUserService iSysUserService;

    @GetMapping("/page")
    public Result<Page<SysUser>> page(SysUser sysUser) {
        return Result.success(iSysUserService.page());
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        return Result.success(iSysUserService.getById(id));
    }

    @PostMapping
    public Result add(@RequestBody SysUser sysUser) {
        return Result.success( iSysUserService.add(sysUser));
    }

    @PutMapping(value = "/{id}")
    public Result update(@PathVariable("id") Long id, @RequestBody SysUser sysUser) {
        return Result.success(iSysUserService.update(id,sysUser));
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable Long[] ids) {
        return Result.success(iSysUserService.delete(ids));
    }
}
