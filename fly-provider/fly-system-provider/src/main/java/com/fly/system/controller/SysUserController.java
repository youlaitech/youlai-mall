package com.fly.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly.common.core.controller.BaseController;
import com.fly.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    public Page<SysUser> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, SysUser sysUser) {
        Page<SysUser> page = new Page<>(pageNum, pageSize);
        Page<SysUser> data = (Page) iSysUserService.page(page,
                new LambdaQueryWrapper<SysUser>()
                        .like(StringUtils.isNotBlank(sysUser.getNickName()), SysUser::getNickName, sysUser.getNickName())
                        .eq(sysUser.getSex() != null, SysUser::getSex, sysUser.getSex())
        );
        return data;
    }

    @GetMapping("/{id}")
    public SysUser get(@PathVariable Long id) {
        SysUser user = iSysUserService.getById(id);
        return user;
    }

    @PostMapping
    public Boolean add(@RequestBody SysUser sysUser) {
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
