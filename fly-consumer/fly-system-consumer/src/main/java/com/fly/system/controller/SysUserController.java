package com.fly.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly.common.core.domain.Result;
import com.fly.system.domain.SysUser;
import com.fly.system.feign.RemoteSysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by XianRui on 2020-03-02 20:58
 **/
@RestController
@RequestMapping("/users")
public class SysUserController {
    @Resource
    private RemoteSysUserService remoteSysUserService;

    @GetMapping("/pageNum/{pageNum}/pageSize/{pageSize}")
    public Result<Page<SysUser>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, SysUser sysUser) {
        Page<SysUser> page = remoteSysUserService.page(pageNum, pageSize, sysUser);
        return Result.success(page);
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        return Result.success(remoteSysUserService.getById(id));
    }

    @PostMapping
    public Result add(@RequestBody SysUser sysUser) {
        return Result.success(remoteSysUserService.add(sysUser));
    }

    @PutMapping(value = "/{id}")
    public Result update(@PathVariable("id") Long id, @RequestBody SysUser sysUser) {
        return Result.success(remoteSysUserService.update(id, sysUser));
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable Long[] ids) {
        return Result.success(remoteSysUserService.delete(ids));
    }
}
