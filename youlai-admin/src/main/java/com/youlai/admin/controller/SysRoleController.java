package com.youlai.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.domain.entity.SysRole;
import com.youlai.admin.service.ISysRoleService;
import com.youlai.common.core.result.PageResult;
import com.youlai.common.core.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "角色接口")
@RestController
@RequestMapping("/roles")
@Slf4j
@AllArgsConstructor
public class SysRoleController {

    private ISysRoleService iSysRoleService;

    @ApiOperation(value = "列表分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "name", value = "角色名称", paramType = "query", dataType = "String"),
    })
    @GetMapping
    public Result list(Integer page, Integer limit, String name) {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<SysRole>()
                .like(StrUtil.isNotBlank(name), SysRole::getName, name)
                .orderByDesc(SysRole::getUpdateTime)
                .orderByDesc(SysRole::getCreateTime);

        if (page != null && limit != null) {
            Page<SysRole> result = iSysRoleService.page(new Page<>(page, limit), queryWrapper);
            return PageResult.success(result.getRecords(), result.getTotal());
        } else if (limit != null) {
            queryWrapper.last("LIMIT " + limit);
        }
        List<SysRole> list = iSysRoleService.list(queryWrapper);
        return Result.success(list);
    }

    @ApiOperation(value = "角色详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "角色id", required = true, paramType = "path", dataType = "Integer")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        SysRole sysRole = iSysRoleService.getById(id);
        return Result.success(sysRole);
    }

    @ApiOperation(value = "新增角色", httpMethod = "POST")
    @ApiImplicitParam(name = "role", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysRole")
    @PostMapping
    public Result add(@RequestBody SysRole role) {
        boolean status = iSysRoleService.add(role);
        return Result.status(status);
    }

    @ApiOperation(value = "修改角色", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "role", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysRole")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Integer id,
            @RequestBody SysRole role) {
        boolean status = iSysRoleService.update(role);
        return Result.status(status);
    }

    @ApiOperation(value = "删除角色", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids[]", value = "id集合", required = true, paramType = "query", allowMultiple = true, dataType = "Integer")
    @DeleteMapping
    public Result delete(@RequestParam("ids") List<Integer> ids) {
        boolean status = iSysRoleService.delete(ids);
        return Result.status(status);
    }


    @ApiOperation(value = "修改角色【局部更新】", httpMethod = "PATCH")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "role", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysRole")
    })
    @PatchMapping(value = "/{id}")
    public Result patch(@PathVariable Integer id, @RequestBody SysRole role) {
        LambdaUpdateWrapper<SysRole> luw = new LambdaUpdateWrapper<SysRole>().eq(SysRole::getId, id);
        if (role.getStatus() != null) { // 状态更新
            luw.set(SysRole::getStatus, role.getStatus());
        } else {
            return Result.error("未发生任何更新");
        }
        boolean status = iSysRoleService.update(luw);
        return Result.status(status);
    }

}
