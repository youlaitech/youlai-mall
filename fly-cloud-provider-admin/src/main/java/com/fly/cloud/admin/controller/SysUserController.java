package com.fly.cloud.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly.common.base.BaseController;
import com.fly.cloud.admin.pojo.entity.SysUser;
import com.fly.cloud.admin.service.ISysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/sss/system/user")
public class SysUserController extends BaseController {

    @Resource
    private ISysUserService iSysUserService;

    @GetMapping("/page")
    public R page(SysUser sysUser) {
        Page<SysUser> page = initPage();
        IPage<SysUser> result = iSysUserService.page(page);
        return R.ok(result);
    }

    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        SysUser user = iSysUserService.getById(id);
        return R.ok(user);
    }

    @PostMapping
    public R add(@RequestBody SysUser sysUser) {
        iSysUserService.save(sysUser);
        return R.ok(null);
    }

    @PutMapping("/{id}")
    public R update(@PathVariable("id") Long id, @RequestBody SysUser sysUser) {
        boolean result = iSysUserService.updateById(sysUser);
        return R.ok(null);
    }

    @GetMapping("/list")
    public R list() {
        List<SysUser> list = iSysUserService.list();
        return R.ok(list);
    }

    @DeleteMapping("/{ids}")
    public R delete(@PathVariable Long[] ids) {
        boolean result = iSysUserService.removeByIds(Arrays.asList(ids));
        return R.ok(null);
    }
}
