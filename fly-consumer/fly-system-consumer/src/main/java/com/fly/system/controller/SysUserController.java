package com.fly.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
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
    public Result<IPage<SysUser>> page(SysUser sysUser) {
        return iSysUserService.page();
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        return iSysUserService.getById(id);
    }

    @PostMapping
    public Result add(@RequestBody SysUser sysUser) {
        return iSysUserService.add(sysUser);
    }

    @PutMapping(value = "/{id}")
    public Result update(@PathVariable("id") Long id, @RequestBody SysUser sysUser) {
        return iSysUserService.update(id,sysUser);
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable Long[] ids) {
        return iSysUserService.delete(ids);
    }

}
