package com.fly.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly.system.pojo.dto.Result;
import com.fly.system.pojo.entity.SysPermission;
import com.fly.system.service.ISysPermissionService;
import com.fly.common.base.BaseController;
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
    public Result<IPage<SysPermission>> page(SysPermission sysUser) {
        Page<SysPermission> page = initPage();
        IPage<SysPermission> data = iSysPermissionService.page(page);
        return Result.success(data);
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        SysPermission user = iSysPermissionService.getById(id);
        return Result.success(user);
    }

    @PostMapping
    public Result add(@RequestBody SysPermission sysUser) {
        return Result.status(iSysPermissionService.save(sysUser));
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
