package com.fly.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly.common.core.controller.BaseController;
import com.fly.system.entity.SysUser;
import com.fly.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class SysUserController extends BaseController {

    @Resource
    private ISysUserService iSysUserService;

    @GetMapping("/page")
    public Page<SysUser> page(SysUser sysUser) {
        Page<SysUser> page = initPage();
        Page<SysUser> data = (Page)iSysUserService.page(page);
        return data;
    }

    @GetMapping("/{id}")
    public SysUser get(@PathVariable Long id) {
        SysUser user = iSysUserService.getById(id);
        return user;
    }

    @PostMapping
    public boolean add(@RequestBody SysUser sysUser) {
        return iSysUserService.save(sysUser);
    }

    @PutMapping(value = "/{id}")
    public boolean update(@PathVariable("id") Long id, @RequestBody SysUser sysUser) {
        return iSysUserService.updateById(sysUser);
    }

    @GetMapping("/list")
    public List<SysUser> list() {
        List<SysUser> list = iSysUserService.list();
        return list;
    }

    @DeleteMapping("/{ids}")
    public boolean delete(@PathVariable Long[] ids) {
        return iSysUserService.removeByIds(Arrays.asList(ids));
    }

    @GetMapping("/username/{username}")
    public SysUser getUserByName(@PathVariable String username) {
        SysUser user = iSysUserService.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserName, username));
        return user;
    }
}
