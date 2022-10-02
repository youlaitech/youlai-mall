package com.youlai.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.system.pojo.entity.SysRole;
import com.youlai.system.pojo.form.RoleForm;
import com.youlai.system.pojo.form.RoleResourceForm;
import com.youlai.system.pojo.query.RolePageQuery;
import com.youlai.system.pojo.vo.role.RolePageVO;
import com.youlai.system.service.SysRoleService;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.common.web.domain.Option;
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

    private final SysRoleService sysRoleService;

    @ApiOperation(value = "角色分页列表")
    @GetMapping("/pages")
    public PageResult<RolePageVO> listRolePages(RolePageQuery queryParams) {
        Page<RolePageVO> result = sysRoleService.listRolePages(queryParams);
        return PageResult.success(result);
    }

    @ApiOperation(value = "角色下拉列表")
    @GetMapping("/options")
    public Result<List<Option>> listRoleOptions() {
        List<Option> list = sysRoleService.listRoleOptions();
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
            @ApiParam("角色状态:1-正常；0-禁用") @RequestParam Integer status
    ) {
        boolean result = sysRoleService.updateRoleStatus(roleId, status);
        return Result.judge(result);
    }

    @ApiOperation(value = "获取角色的资源ID集合", notes = "资源包括菜单和权限ID")
    @GetMapping("/{roleId}/resources")
    public Result<RoleResourceForm> getRoleResources(
            @ApiParam("角色ID") @PathVariable Long roleId
    ) {
        RoleResourceForm resourceIds = sysRoleService.getRoleResources(roleId);
        return Result.success(resourceIds);
    }

    @ApiOperation(value = "分配角色的资源权限")
    @PutMapping("/{roleId}/resources")
    public Result updateRoleResource(
            @PathVariable Long roleId,
            @RequestBody RoleResourceForm roleResourceForm
    ) {
        boolean result = sysRoleService.updateRoleResource(roleId,roleResourceForm);
        return Result.judge(result);
    }
}
