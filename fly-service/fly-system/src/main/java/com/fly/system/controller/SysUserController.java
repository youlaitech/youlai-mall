package com.fly.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly.common.base.BaseController;
import com.fly.system.pojo.dto.Result;
import com.fly.system.pojo.entity.SysUser;
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
    public Result<IPage<SysUser>> page(SysUser sysUser) {
        Page<SysUser> page = initPage();
        IPage<SysUser> data = iSysUserService.page(page);
        return Result.success(data);
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        SysUser user = iSysUserService.getById(id);
        if(user==null){
            throw new RuntimeException("用户不存在");
        }
      return Result.success(user);
    }

    @PostMapping
    public Result add(@RequestBody SysUser sysUser) {
        return Result.status(iSysUserService.save(sysUser));
    }

    @PutMapping(value = "/{id}")
    public Result update(@PathVariable("id") Long id, @RequestBody SysUser sysUser) {
        boolean result= iSysUserService.updateById(sysUser);
        return Result.success(result);
    }

    @GetMapping("/list")
    public Result list() {
        List<SysUser> list = iSysUserService.list();
        return Result.success(list);
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable Long[] ids) {
        boolean result= iSysUserService.removeByIds(Arrays.asList(ids));
        return Result.success(result);
    }

    @GetMapping("/username/{username}")
    public Result<SysUser> getUserByName(@PathVariable String username){
        SysUser user = iSysUserService.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserName, username));
        return Result.success(user);
    }




}
