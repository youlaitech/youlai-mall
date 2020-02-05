package com.fly.cloud.admin.biz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly.cloud.admin.biz.service.ISysUserService;
import com.fly.common.base.BaseController;
import com.fly.common.pojo.entity.SysUser;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController {

    @Resource
    private ISysUserService iSysUserService;

    @GetMapping("/page")
    public IPage<SysUser> page(SysUser sysUser) {
        Page<SysUser> page = initPage();
        IPage<SysUser> result = iSysUserService.page(page);
        return result;
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
    public  List<SysUser> list() {
        List<SysUser> list = iSysUserService.list();
        return list;
    }

    @DeleteMapping("/{ids}")
    public boolean delete(@PathVariable Long[] ids) {
        return iSysUserService.removeByIds(Arrays.asList(ids));
    }
}
