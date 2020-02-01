package com.fly.floud.admin.controller;


import com.fly.common.pojo.entity.SysUser;
import com.fly.floud.admin.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SysUserController {

    @Autowired
    private ISysUserService iSysUserService;

    @GetMapping("/user/{id}")
    public SysUser get(@PathVariable Long id){
        SysUser sysUser = iSysUserService.get(id);
        return sysUser;
    }

}
