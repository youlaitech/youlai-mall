package com.youlai.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.pojo.entity.SysRole;
import com.youlai.admin.pojo.form.RoleForm;
import com.youlai.admin.pojo.form.RolePermsForm;
import com.youlai.admin.pojo.query.RolePageQuery;
import com.youlai.admin.pojo.vo.role.RolePageVO;
import com.youlai.admin.service.SysPermissionService;
import com.youlai.admin.service.SysRoleMenuService;
import com.youlai.admin.service.SysRolePermissionService;
import com.youlai.admin.service.SysRoleService;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.common.web.domain.Option;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "角色接口")
@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService sysRoleService;
    private final SysRoleMenuService sysRoleMenuService;
    private final SysRolePermissionService sysRolePermissionService;
    private final SysPermissionService sysPermissionService;

    @ApiOperation(value = "角色分页列表")
    @GetMapping("/page_list")
    public PageResult<RolePageVO> listPageRoles(RolePageQuery queryParams) {
        Page<RolePageVO> result = sysRoleService.listPageRoles(queryParams);
        return PageResult.success(result);
    }

    @ApiOperation(value = "角色下拉列表")
    @GetMapping("/select_list")
    public Result<List<Option>> listSelectRoles() {
        List<Option> list = sysRoleService.listSelectRoles();
        return Result.success(list);
    }

    @ApiOperation(value = "角色详情")
    @GetMapping("/{roleId}")
    public Result getRoleDetail(
            @ApiParam("角色ID") @PathVariable Long roleId
    ) {
        SysRole role = sysRoleService.getById(roleId);
        return Result.success(role);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping
    public Result addRole(@Valid @RequestBody RoleForm roleForm) {
        boolean result = sysRoleService.saveRole(roleForm);
        return Result.judge(result);
    }

    @ApiOperation(value = "修改角色")
    @PutMapping(value = "/{id}")
    public Result updateRole(@Valid @RequestBody RoleForm roleForm) {
        boolean result = sysRoleService.saveRole(roleForm);
        return Result.judge(result);
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/{ids}")
    public Result deleteRoles(
            @ApiParam("删除角色，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = sysRoleService.deleteRoles(ids);
        return Result.judge(result);
    }

    @ApiOperation(value = "修改角色状态")
    @PutMapping(value = "/{roleId}/status")
    public Result updateRoleStatus(
            @ApiParam("角色ID") @PathVariable Long roleId,
            @ApiParam("角色状态(1：启用；0：禁用)") @RequestParam Integer status
    ) {
        boolean result = sysRoleService.updateRoleStatus(roleId, status);
        return Result.judge(result);
    }

    @ApiOperation(value = "获取角色的资源ID集合",notes = "资源包括菜单(m_id)和权限(p_id)")
    @GetMapping("/{roleId}/resource_ids")
    public Result listRoleResourceIds(
            @ApiParam("角色ID") @PathVariable Long roleId
    ) {
        List<String> resourceIds = sysRoleService.listRoleResourceIds(roleId);
        return Result.success(resourceIds);
    }

    @ApiOperation(value = "获取角色的权限ID集合")
    @GetMapping("/{roleId}/permissions")
    public Result listRolePermission(
            @ApiParam("角色ID") @PathVariable Long roleId,
            @ApiParam("菜单ID") Long menuId
    ) {
        List<Long> permissionIds = sysRolePermissionService.listPermIds(menuId, roleId);
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
        boolean result = sysRoleMenuService.update(roleId, menuIds);
        if (result) {
            sysPermissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }

    @ApiOperation(value = "修改角色权限")
    @PutMapping(value = "/{roleId}/permissions")
    public Result saveRolePerms(
            @ApiParam("角色ID") @PathVariable Long roleId,
            @RequestBody RolePermsForm rolePerms) {
        rolePerms.setRoleId(roleId);
        boolean result = sysRolePermissionService.saveRolePerms(rolePerms);
        if (result) {
            sysPermissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }
}
