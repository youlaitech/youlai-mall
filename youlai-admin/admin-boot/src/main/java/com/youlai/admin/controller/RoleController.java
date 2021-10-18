package com.youlai.admin.controller;

import cn.hutool.core.lang.Assert;
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
import com.youlai.common.result.Result;
import com.youlai.common.web.util.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "角色接口")
@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final ISysRoleService iSysRoleService;
    private final ISysRoleMenuService iSysRoleMenuService;
    private final ISysRolePermissionService iSysRolePermissionService;
    private final ISysPermissionService iSysPermissionService;


    @ApiOperation(value = "列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "name", value = "角色名称", paramType = "query", dataType = "String"),
    })
    @GetMapping("/page")
    public Result pageList(Integer page, Integer limit, String name) {
        List<String> roles = JwtUtils.getRoles();
        boolean isRoot = roles.contains(GlobalConstants.ROOT_ROLE_CODE);  // 判断是否是超级管理员
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<SysRole>()
                .like(StrUtil.isNotBlank(name), SysRole::getName, name)
                .ne(!isRoot, SysRole::getCode, GlobalConstants.ROOT_ROLE_CODE)
                .orderByAsc(SysRole::getSort)
                .orderByDesc(SysRole::getGmtModified)
                .orderByDesc(SysRole::getGmtCreate);
        Page<SysRole> result = iSysRoleService.page(new Page<>(page, limit), queryWrapper);
        return Result.success(result.getRecords(), result.getTotal());
    }


    @ApiOperation(value = "角色列表")
    @GetMapping
    public Result list() {
        List<String> roles = JwtUtils.getRoles();
        boolean isRoot = roles.contains(GlobalConstants.ROOT_ROLE_CODE);  // 判断是否是超级管理员
        List list = iSysRoleService.list(new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getStatus, GlobalConstants.STATUS_YES)
                .ne(!isRoot, SysRole::getCode, GlobalConstants.ROOT_ROLE_CODE)
                .orderByAsc(SysRole::getSort)
        );
        return Result.success(list);
    }


    @ApiOperation(value = "新增角色")
    @ApiImplicitParam(name = "role", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysRole")
    @PostMapping
    public Result add(@RequestBody SysRole role) {
        int count = iSysRoleService.count(new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getCode, role.getCode())
                .or()
                .eq(SysRole::getName, role.getName())
        );
        Assert.isTrue(count == 0, "角色名称或角色编码重复，请检查！");
        boolean result = iSysRoleService.save(role);
        if (result) {
            iSysPermissionService.refreshPermRolesRules();
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
        int count = iSysRoleService.count(new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getCode, role.getCode())
                .or()
                .eq(SysRole::getName, role.getName())
                .ne(SysRole::getId, id)
        );
        Assert.isTrue(count == 0, "角色名称或角色编码重复，请检查！");
        boolean result = iSysRoleService.updateById(role);
        if (result) {
            iSysPermissionService.refreshPermRolesRules();
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
            iSysPermissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }

    @ApiOperation(value = "选择性修改角色")
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
            iSysPermissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }

    @ApiOperation(value = "获取角色拥有的菜单ID集合")
    @ApiImplicitParam(name = "id", value = "角色id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}/menus")
    public Result listRoleMenu(@PathVariable("id") Long roleId) {
        List<Long> menuIds = iSysRoleMenuService.listMenuIds(roleId);
        return Result.success(menuIds);
    }

    @ApiOperation(value = "获取角色拥有的权限ID集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "menuId", value = "菜单ID", paramType = "query", dataType = "Integer"),
    })
    @GetMapping("/{id}/permissions")
    public Result listRolePermission(@PathVariable("id") Long roleId, Long menuId) {
        List<Long> permissionIds = iSysRolePermissionService.listPermissionId(menuId, roleId);
        return Result.success(permissionIds);
    }

    @ApiOperation(value = "修改角色菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "role", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysRole")
    })
    @PutMapping(value = "/{id}/menus")
    public Result updateRoleMenu(
            @PathVariable("id") Long roleId,
            @RequestBody SysRole role) {

        List<Long> menuIds = role.getMenuIds();
        boolean result = iSysRoleMenuService.update(roleId, menuIds);
        if (result) {
            iSysPermissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }

    @ApiOperation(value = "修改角色权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "rolePermission", value = "实体JSON对象", required = true, paramType = "body", dataType = "RolePermissionDTO")
    })
    @PutMapping(value = "/{id}/permissions")
    public Result updateRolePermission(
            @PathVariable("id") Long roleId,
            @RequestBody RolePermissionDTO rolePermission) {
        rolePermission.setRoleId(roleId);
        boolean result = iSysRolePermissionService.update(rolePermission);
        if (result) {
            iSysPermissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }
}
