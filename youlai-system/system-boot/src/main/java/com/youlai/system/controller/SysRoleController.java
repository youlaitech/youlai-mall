package com.youlai.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.common.web.model.Option;
import com.youlai.system.pojo.entity.SysRole;
import com.youlai.system.pojo.form.RoleForm;
import com.youlai.system.pojo.query.RolePageQuery;
import com.youlai.system.service.SysRoleService;
import com.youlai.system.pojo.vo.role.RolePageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "角色接口")
@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class SysRoleController {
    private final SysRoleService roleService;

    @ApiOperation(value = "角色分页列表")
    @GetMapping("/pages")
    public PageResult<RolePageVO> listRolePages(RolePageQuery queryParams) {
        Page<RolePageVO> result = roleService.listRolePages(queryParams);
        return PageResult.success(result);
    }

    @ApiOperation(value = "角色下拉列表")
    @GetMapping("/options")
    public Result<List<Option>> listRoleOptions() {
        List<Option> list = roleService.listRoleOptions();
        return Result.success(list);
    }

    @ApiOperation(value = "角色详情")
    @GetMapping("/{roleId}")
    public Result getRoleDetail(
            @ApiParam("角色ID") @PathVariable Long roleId
    ) {
        SysRole role = roleService.getById(roleId);
        return Result.success(role);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping
    public Result addRole(@Valid @RequestBody RoleForm roleForm) {
        boolean result = roleService.saveRole(roleForm);
        return Result.judge(result);
    }

    @ApiOperation(value = "修改角色")
    @PutMapping(value = "/{id}")
    public Result updateRole(@Valid @RequestBody RoleForm roleForm) {
        boolean result = roleService.saveRole(roleForm);
        return Result.judge(result);
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/{ids}")
    public Result deleteRoles(
            @ApiParam("删除角色，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = roleService.deleteRoles(ids);
        return Result.judge(result);
    }

    @ApiOperation(value = "修改角色状态")
    @PutMapping(value = "/{roleId}/status")
    public Result updateRoleStatus(
            @ApiParam("角色ID") @PathVariable Long roleId,
            @ApiParam("角色状态:1-启用；0-禁用") @RequestParam Integer status
    ) {
        boolean result = roleService.updateRoleStatus(roleId, status);
        return Result.judge(result);
    }

    @ApiOperation(value = "获取角色的菜单ID集合")
    @GetMapping("/{roleId}/menuIds")
    public Result<List<Long>> getRoleMenuIds(
            @ApiParam("角色ID") @PathVariable Long roleId
    ) {
        List<Long> resourceIds = roleService.getRoleMenuIds(roleId);
        return Result.success(resourceIds);
    }

    @ApiOperation(value = "分配角色的资源权限")
    @PutMapping("/{roleId}/menus")
    public Result updateRoleMenus(
            @PathVariable Long roleId,
            @RequestBody List<Long> menuIds
    ) {
        boolean result = roleService.updateRoleMenus(roleId,menuIds);
        return Result.judge(result);
    }
}
