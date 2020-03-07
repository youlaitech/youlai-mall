package com.fly.system.controller;

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
    public Result page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, SysUser sysUser) {
        Result result = remoteSysUserService.page(pageNum, pageSize, sysUser);
        if (result.getCode() == Result.Status.SUCCESS.value()) {
            return Result.success(result.getData());
        }
        return Result.error();
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        Result<SysUser> result = remoteSysUserService.getById(id);
        if (result.getCode() == Result.Status.SUCCESS.value()) {
            return Result.success(result.getData());
        }
        return Result.error();
    }

    @PostMapping
    public Result add(@RequestBody SysUser sysUser) {
        Result result = remoteSysUserService.add(sysUser);
        if (result.getCode() == Result.Status.SUCCESS.value()) {
            return Result.success();
        }
        return Result.error();
    }

    @PutMapping(value = "/{id}")
    public Result update(@PathVariable("id") Long id, @RequestBody SysUser sysUser) {
        Result result = remoteSysUserService.update(id, sysUser);
        if (result.getCode() == Result.Status.SUCCESS.value()) {
            return Result.success(result.getData());
        }
        return Result.error();
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable Long[] ids) {
        Result result = remoteSysUserService.delete(ids);
        if (result.getCode() == Result.Status.SUCCESS.value()) {
            return Result.success();
        }
        return Result.error();
    }
}
