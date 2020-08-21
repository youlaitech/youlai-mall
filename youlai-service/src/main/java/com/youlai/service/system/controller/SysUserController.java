package com.youlai.service.system.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.service.system.entity.SysUser;
import com.youlai.service.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Api(tags = "用户接口")
@RestController
@RequestMapping("/users")
@Slf4j
public class SysUserController {


    @Autowired
    private ISysUserService iSysUserService;

    @ApiOperation(value = "列表分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "nickname", value = "姓名", paramType = "query", dataType = "String"),
    })
    @GetMapping
    public Result list(Integer page, Integer limit, String username, String nickname) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<SysUser>()
                .like(StrUtil.isNotBlank(username), SysUser::getUsername, username)
                .like(StrUtil.isNotBlank(nickname), SysUser::getNickname, nickname)
                .orderByDesc(SysUser::getUpdateTime)
                .orderByDesc(SysUser::getCreateTime);

        if (page != null && limit != null) {
            Page<SysUser> result = iSysUserService.page(new Page<>(page, limit) ,queryWrapper);

            return PageResult.success(result.getRecords(), result.getTotal());
        } else if (limit != null) {
            queryWrapper.last("LIMIT " + limit);
        }
        List<SysUser> list = iSysUserService.list(queryWrapper);
        return Result.success(list);
    }

    @ApiOperation(value = "用户详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        SysUser sysUser = iSysUserService.getById(id);
        return Result.success(sysUser);
    }

    @ApiOperation(value = "新增用户", httpMethod = "POST")
    @ApiImplicitParam(name = "sysUser", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysUser")
    @PostMapping
    public Result add(@RequestBody SysUser sysUser) {
        boolean status = iSysUserService.save(sysUser);
        return Result.status(status);
    }

    @ApiOperation(value = "修改用户", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "sysUser", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysUser")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Long id,
            @RequestBody SysUser sysUser) {
        sysUser.setUpdateTime(new Date());
        boolean status = iSysUserService.updateById(sysUser);
        return Result.status(status);
    }

    @ApiOperation(value = "删除用户", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids[]", value = "id集合", required = true, paramType = "query", allowMultiple = true, dataType = "Long")
    @DeleteMapping
    public Result delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iSysUserService.removeByIds(ids);
        return Result.status(status);
    }

}
