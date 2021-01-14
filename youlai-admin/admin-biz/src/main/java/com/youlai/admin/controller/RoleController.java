package com.youlai.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.pojo.SysRole;
import com.youlai.admin.service.ISysRoleService;
import com.youlai.common.core.constant.SystemConstants;
import com.youlai.common.core.enums.QueryModeEnum;
import com.youlai.common.core.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "角色接口")
@RestController
@RequestMapping("/api.admin/v1/roles")
@Slf4j
@AllArgsConstructor
public class RoleController {

    private ISysRoleService iSysRoleService;

    @ApiOperation(value = "列表分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryMode", paramType = "query", dataType = "QueryModeEnum"),
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "name", value = "角色名称", paramType = "query", dataType = "String"),
    })
    @GetMapping
    public Result list(String queryMode, Integer page, Integer limit, String name) {

        QueryModeEnum queryModeEnum = QueryModeEnum.getValue(queryMode);
        List list = null;
        switch (queryModeEnum) {
            case PAGE:
                LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<SysRole>()
                        .like(StrUtil.isNotBlank(name), SysRole::getName, name)
                        .orderByAsc(SysRole::getSort)
                        .orderByDesc(SysRole::getGmtModified)
                        .orderByDesc(SysRole::getGmtCreate);
                Page<SysRole> result = iSysRoleService.page(new Page<>(page, limit), queryWrapper);
                return Result.success(result.getRecords(), result.getTotal());
            case LIST:
                list = iSysRoleService.list(new LambdaQueryWrapper<SysRole>()
                        .eq(SysRole::getStatus, SystemConstants.STATUS_NORMAL_VALUE));

        }
        return Result.success(list);
    }

    @ApiOperation(value = "角色详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "角色id", required = true, paramType = "path", dataType = "Integer")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
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
            @ApiImplicitParam(name = "id", value = "角色id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "role", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysRole")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Long id,
            @RequestBody SysRole role) {
        boolean status = iSysRoleService.update(role);
        return Result.status(status);
    }

    @ApiOperation(value = "删除角色", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "id集合", required = true, paramType = "query", allowMultiple = true, dataType = "Long")
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable String ids) {
        boolean status = iSysRoleService.delete(Arrays.asList(ids.split(",")).stream()
                .map(id -> Long.parseLong(id)).collect(Collectors.toList()));
        return Result.status(status);
    }

    @ApiOperation(value = "修改角色【局部更新】", httpMethod = "PATCH")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "role", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysRole")
    })
    @PatchMapping(value = "/{id}")
    public Result patch(@PathVariable Long id, @RequestBody SysRole role) {
        if (role.getPermissionIds() != null) {
            boolean status = iSysRoleService.update(id, role.getPermissionIds());
            return Result.status(status);
        }
        LambdaUpdateWrapper<SysRole> updateWrapper = new LambdaUpdateWrapper<SysRole>()
                .eq(SysRole::getId, id)
                .set(role.getStatus() != null, SysRole::getStatus, role.getStatus());
        boolean status = iSysRoleService.update(updateWrapper);
        return Result.status(status);

    }

}
