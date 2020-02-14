package com.fly.cloud.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly.cloud.system.service.ISysUserService;
import com.fly.cloud.system.utils.SecurityUtils;
import com.fly.common.base.BaseController;
import com.fly.common.pojo.dto.Result;
import com.fly.common.pojo.entity.SysUser;
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
    public Result<IPage<SysUser>> page(SysUser sysUser) {
        Page<SysUser> page = initPage();
        IPage<SysUser> data = iSysUserService.page(page);
        return Result.success(data);
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        SysUser user = iSysUserService.getById(id);
        return Result.success(user);
    }

    @PostMapping
    public Result add(@RequestBody SysUser sysUser) {
        return Result.status(iSysUserService.save(sysUser));
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

    @GetMapping("/username/{username}")
    public Result<SysUser> getUserByName(@PathVariable String username){
        SysUser user = iSysUserService.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserName, username));
        return Result.success(user);
    }

    @GetMapping("/currentUser/info")
    public Result<SysUser> info(){
        String username = SecurityUtils.getUsername();
        SysUser user = iSysUserService.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserName, username));
        return Result.success(user);
    }

}
