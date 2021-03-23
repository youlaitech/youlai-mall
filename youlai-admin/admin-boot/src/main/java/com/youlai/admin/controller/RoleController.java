package com.youlai.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.pojo.dto.RolePermissionDTO;
import com.youlai.admin.pojo.entity.SysRole;
import com.youlai.admin.service.ISysPermissionService;
import com.youlai.admin.service.ISysRoleMenuService;
import com.youlai.admin.service.ISysRolePermissionService;
import com.youlai.admin.service.ISysRoleService;
import com.youlai.common.constant.GlobalConstants;
import com.youlai.common.enums.QueryModeEnum;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
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

    private ISysRoleMenuService iSysRoleMenuService;

    private ISysRolePermissionService iSysRolePermissionService;

    private ISysPermissionService iSysPermissionService;

    @ApiOperation(value = "列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryMode", paramType = "query", dataType = "QueryModeEnum"),
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "name", value = "角色名称", paramType = "query", dataType = "String"),
    })
    @GetMapping
    public Result list(
            String queryMode,
            Integer page,
            Integer limit,
            String name
    ) {
        QueryModeEnum queryModeEnum = QueryModeEnum.getValue(queryMode);
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
                List list = iSysRoleService.list(new LambdaQueryWrapper<SysRole>()
                        .eq(SysRole::getStatus, GlobalConstants.STATUS_NORMAL_VALUE));
                return Result.success(list);
            default:
                return Result.failed(ResultCode.QUERY_MODE_IS_NULL);

        }
    }


    @ApiOperation(value = "新增角色")
    @ApiImplicitParam(name = "role", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysRole")
    @PostMapping
    public Result add(@RequestBody SysRole role) {
        boolean result = iSysRoleService.save(role);
        if (result) {
            iSysPermissionService.refreshPermissionRolesCache();
        }
        return Result.judge(result);
    }

    @ApiOperation(value = "修改角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "role", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysRole")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Long id,
            @RequestBody SysRole role) {
        boolean result = iSysRoleService.updateById(role);
        if (result) {
            iSysPermissionService.refreshPermissionRolesCache();
        }
        return Result.judge(result);
    }

    @ApiOperation(value = "删除角色")
    @ApiImplicitParam(name = "ids", value = "以,分割拼接字符串", required = true, dataType = "String")
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable String ids) {
        boolean result = iSysRoleService.delete(Arrays.asList(ids.split(",")).stream()
                .map(id -> Long.parseLong(id)).collect(Collectors.toList()));
        if (result) {
            iSysPermissionService.refreshPermissionRolesCache();
        }
        return Result.judge(result);
    }

    @ApiOperation(value = "局部更新角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "role", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysRole")
    })
    @PatchMapping(value = "/{id}")
    public Result patch(@PathVariable Long id, @RequestBody SysRole role) {
        LambdaUpdateWrapper<SysRole> updateWrapper = new LambdaUpdateWrapper<SysRole>()
                .eq(SysRole::getId, id)
                .set(role.getStatus() != null, SysRole::getStatus, role.getStatus());
        boolean result = iSysRoleService.update(updateWrapper);
        if (result) {
            iSysPermissionService.refreshPermissionRolesCache();
        }
        return Result.judge(result);
    }

    @ApiOperation(value = "角色拥有的菜单ID集合")
    @ApiImplicitParam(name = "id", value = "角色id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}/menu_ids")
    public Result roleMenuIds(@PathVariable("id") Long roleId) {
        List<Long> menuIds = iSysRoleMenuService.listMenuIds(roleId);
        return Result.success(menuIds);
    }

    @ApiOperation(value = "角色拥有的权限ID集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "type", value = "权限类型", paramType = "query", dataType = "Integer"),
    })
    @GetMapping("/{id}/permission_ids")
    public Result rolePermissionIds(@PathVariable("id") Long roleId, @RequestParam Integer type) {
        List<Long> permissionIds = iSysRolePermissionService.listPermissionIds(roleId, type);
        return Result.success(permissionIds);
    }


    @ApiOperation(value = "修改角色菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "role", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysRole")
    })
    @PutMapping(value = "/{id}/menu_ids")
    public Result updateRoleMenuIds(
            @PathVariable("id") Long roleId,
            @RequestBody SysRole role) {

        List<Long> menuIds = role.getMenuIds();
        boolean result = iSysRoleMenuService.update(roleId, menuIds);
        return Result.judge(result);
    }

    @ApiOperation(value = "修改角色权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "rolePermission", value = "实体JSON对象", required = true, paramType = "body", dataType = "RolePermissionDTO")
    })
    @PutMapping(value = "/{id}/permission_ids")
    public Result updateRolePermissionIds(
            @PathVariable("id") Long roleId,
            @RequestBody RolePermissionDTO rolePermission) {
        rolePermission.setRoleId(roleId);
        boolean result = iSysRolePermissionService.update(rolePermission);
        if (result) {
            iSysPermissionService.refreshPermissionRolesCache();
        }
        return Result.judge(result);
    }
}
