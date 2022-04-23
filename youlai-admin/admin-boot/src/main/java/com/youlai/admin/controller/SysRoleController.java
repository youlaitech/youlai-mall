package com.youlai.admin.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.pojo.entity.SysRole;
import com.youlai.admin.pojo.form.RolePermsForm;
import com.youlai.admin.service.ISysPermissionService;
import com.youlai.admin.service.ISysRoleMenuService;
import com.youlai.admin.service.ISysRolePermissionService;
import com.youlai.admin.service.ISysRoleService;
import com.youlai.common.constant.GlobalConstants;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.common.web.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "角色接口")
@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class SysRoleController {

    private final ISysRoleService iSysRoleService;
    private final ISysRoleMenuService iSysRoleMenuService;
    private final ISysRolePermissionService iSysRolePermissionService;
    private final ISysPermissionService iSysPermissionService;

    @ApiOperation(value = "列表分页")
    @GetMapping("/page")
    public PageResult<SysRole> getRolePageList(
            @ApiParam("页码") long pageNum,
            @ApiParam("每页数量") long pageSize,
            @ApiParam("角色名称") String name
    ) {
        List<String> roles = UserUtils.getRoles();
        boolean isRoot = roles.contains(GlobalConstants.ROOT_ROLE_CODE);  // 判断是否是超级管理员
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<SysRole>()
                .like(StrUtil.isNotBlank(name), SysRole::getName, name)
                .ne(!isRoot, SysRole::getCode, GlobalConstants.ROOT_ROLE_CODE)
                .orderByAsc(SysRole::getSort)
                .orderByDesc(SysRole::getGmtModified)
                .orderByDesc(SysRole::getGmtCreate);
        Page<SysRole> result = iSysRoleService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return PageResult.success(result);
    }

    @ApiOperation(value = "角色列表")
    @GetMapping
    public Result getRoleList() {
        List<String> roles = UserUtils.getRoles();
        boolean isRoot = roles.contains(GlobalConstants.ROOT_ROLE_CODE);  // 判断是否是超级管理员
        List list = iSysRoleService.list(new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getStatus, GlobalConstants.STATUS_YES)
                .ne(!isRoot, SysRole::getCode, GlobalConstants.ROOT_ROLE_CODE)
                .orderByAsc(SysRole::getSort)
        );
        return Result.success(list);
    }

    @ApiOperation(value = "角色详情")
    @GetMapping("/{roleId}")
    public Result getRoleDetail(
            @ApiParam("角色ID") @PathVariable Long roleId
    ) {
        SysRole role = iSysRoleService.getById(roleId);
        return Result.success(role);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping
    public Result saveRole(@RequestBody SysRole role) {
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
    @PutMapping(value = "/{id}")
    public Result updateRole(
            @ApiParam("角色ID") @PathVariable Long id,
            @RequestBody SysRole role) {
        int count = iSysRoleService.count(new LambdaQueryWrapper<SysRole>()
                .ne(SysRole::getId, id)
                .and(wrapper ->
                        wrapper.eq(SysRole::getCode, role.getCode())
                                .or()
                                .eq(SysRole::getName, role.getName())
                ));
        Assert.isTrue(count == 0, "角色名称或角色编码重复，请检查！");
        boolean result = iSysRoleService.updateById(role);
        if (result) {
            iSysPermissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/{ids}")
    public Result deleteRoles(
            @ApiParam("删除角色，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = iSysRoleService.delete(Arrays.asList(ids.split(",")).stream()
                .map(id -> Long.parseLong(id)).collect(Collectors.toList()));
        if (result) {
            iSysPermissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }

    @ApiOperation(value = "选择性修改角色")
    @PatchMapping(value = "/{roleId}")
    public Result updateRolePart(
            @ApiParam("角色ID") @PathVariable Long roleId,
            @RequestBody SysRole role
    ) {
        LambdaUpdateWrapper<SysRole> updateWrapper = new LambdaUpdateWrapper<SysRole>()
                .eq(SysRole::getId, roleId)
                .set(role.getStatus() != null, SysRole::getStatus, role.getStatus());
        boolean result = iSysRoleService.update(updateWrapper);
        if (result) {
            iSysPermissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }

    @ApiOperation(value = "获取角色的菜单ID集合")
    @ApiImplicitParam(name = "roleId", value = "角色id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{roleId}/menu_ids")
    public Result listRoleMenuIds(
            @ApiParam("角色ID") @PathVariable Long roleId
    ) {
        List<Long> menuIds = iSysRoleMenuService.listMenuIds(roleId);
        return Result.success(menuIds);
    }

    @ApiOperation(value = "获取角色的权限ID集合")
    @GetMapping("/{roleId}/permissions")
    public Result listRolePermission(
            @ApiParam("角色ID") @PathVariable Long roleId,
            @ApiParam("菜单ID") Long menuId
    ) {
        List<Long> permissionIds = iSysRolePermissionService.listPermIds(menuId, roleId);
        return Result.success(permissionIds);
    }

    @ApiOperation(value = "修改角色菜单")
    @CacheEvict(cacheNames = "system", key = "'routes'")
    @PutMapping(value = "/{roleId}/menus")
    public Result updateRoleMenu(
            @ApiParam("角色ID") @PathVariable Long roleId,
            @RequestBody SysRole role
    ) {
        List<Long> menuIds = role.getMenuIds();
        boolean result = iSysRoleMenuService.update(roleId, menuIds);
        if (result) {
            iSysPermissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }


    @ApiOperation(value = "修改角色权限")
    @PutMapping(value = "/{roleId}/permissions")
    public Result saveRolePerms(
            @ApiParam("角色ID") @PathVariable Long roleId,
            @RequestBody RolePermsForm rolePerms) {
        rolePerms.setRoleId(roleId);
        boolean result = iSysRolePermissionService.saveRolePerms(rolePerms);
        if (result) {
            iSysPermissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }
}
