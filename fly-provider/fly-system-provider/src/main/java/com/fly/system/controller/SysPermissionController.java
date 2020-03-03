package com.fly.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly.system.entity.SysPermission;
import com.fly.system.service.ISysPermissionService;
import com.fly.common.core.controller.BaseController;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/system/permissions")
public class SysPermissionController extends BaseController {

    @Resource
    private ISysPermissionService iSysPermissionService;

    @GetMapping("/page")
    public Page<SysPermission> page(SysPermission sysUser) {
        Page<SysPermission> page = initPage();
        Page<SysPermission> data = (Page)iSysPermissionService.page(page);
        return data;
    }

    @GetMapping("/{id}")
    public SysPermission get(@PathVariable Long id) {
        SysPermission permission= iSysPermissionService.getById(id);
        return permission;
    }

    @PostMapping
    public boolean add(@RequestBody SysPermission sysUser) {
        return iSysPermissionService.save(sysUser);
    }

    @PutMapping(value = "/{id}")
    public boolean update(@PathVariable("id") Long id, @RequestBody SysPermission sysUser) {
       return iSysPermissionService.updateById(sysUser);
    }

    @GetMapping("/list")
    public  List<SysPermission> list() {
        List<SysPermission> list = iSysPermissionService.list();
        return list;
    }

    @DeleteMapping("/{ids}")
    public boolean delete(@PathVariable Long[] ids) {
        return iSysPermissionService.removeByIds(Arrays.asList(ids));
    }
}
