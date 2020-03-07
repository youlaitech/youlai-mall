package com.fly.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly.common.core.controller.BaseController;
import com.fly.common.core.domain.Result;
import com.fly.system.service.ISysUserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import com.fly.system.pojo.entity.SysUser;

@Api(tags = "用户接口")
@RestController
@RequestMapping("/users")
@Slf4j
public class SysUserController extends BaseController {

    @Resource
    private ISysUserService iSysUserService;

    @GetMapping("/pageNum/{pageNum}/pageSize/{pageSize}")
    public Result<Page<SysUser>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, SysUser sysUser) {
        Page<SysUser> page = new Page<>(pageNum, pageSize);
        Page<SysUser> data = (Page) iSysUserService.page(page,
                new LambdaQueryWrapper<SysUser>()
                        .like(StringUtils.isNotBlank(sysUser.getNickName()), SysUser::getNickName, sysUser.getNickName())
                        .eq(sysUser.getSex() != null, SysUser::getSex, sysUser.getSex())
        );
        return Result.success(data);
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        SysUser user = iSysUserService.getById(id);
        return Result.success(user);
    }

    @PostMapping
    public Result add(@RequestBody SysUser sysUser) {
        boolean status = iSysUserService.save(sysUser);
        return Result.status(status);
    }

    @PutMapping(value = "/{id}")
    public Result update(@PathVariable("id") Long id, @RequestBody SysUser sysUser) {
        boolean status = iSysUserService.updateById(sysUser);
        return Result.status(status);
    }

    @GetMapping("/list")
    public Result list() {
        List<SysUser> list = iSysUserService.list();
        return Result.success(list);
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable Long[] ids) {
        boolean status = iSysUserService.removeByIds(Arrays.asList(ids));
        return Result.status(status);
    }

    @GetMapping("/username/{username}")
    public Result getUserByName(@PathVariable String username) {
        SysUser user = iSysUserService.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserName, username));
        return Result.success(user);
    }
}
