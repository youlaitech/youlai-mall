package com.youlai.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.admin.pojo.entity.SysPermission;
import com.youlai.admin.pojo.query.PermPageQuery;
import com.youlai.admin.pojo.vo.perm.PermPageVO;
import com.youlai.admin.service.SysPermissionService;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Api(tags = "权限接口")
@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
public class SysPermissionController {

    private final SysPermissionService sysPermissionService;

    @ApiOperation(value = "权限分页列表")
    @GetMapping("/page")
    public PageResult<PermPageVO> listPermPages(PermPageQuery permPageQuery) {
        IPage<PermPageVO> result = sysPermissionService.listPermPages(permPageQuery);
        return PageResult.success(result);
    }

    @ApiOperation(value = "权限列表")
    @GetMapping
    public Result listPermissions(
            @ApiParam(value = "菜单ID") @RequestParam(required = false) Long menuId
    ) {
        List<SysPermission> list = sysPermissionService.list(
                new LambdaQueryWrapper<SysPermission>()
                        .eq(menuId != null, SysPermission::getMenuId, menuId)
        );
        return Result.success(list);
    }

    @ApiOperation(value = "权限详情")
    @GetMapping("/{permissionId}")
    public Result getPermissionDetail(
            @ApiParam("权限ID") @PathVariable Long permissionId
    ) {
        SysPermission permission = sysPermissionService.getById(permissionId);
        return Result.success(permission);
    }

    @ApiOperation(value = "新增权限")
    @PostMapping
    public Result addPerm(
            @RequestBody SysPermission permission
    ) {
        boolean result = sysPermissionService.save(permission);
        if (result) {
            sysPermissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }

    @ApiOperation(value = "修改权限")
    @PutMapping(value = "/{permissionId}")
    public Result updatePerm(
            @ApiParam("权限ID") @PathVariable Long permissionId,
            @RequestBody SysPermission permission
    ) {
        boolean result = sysPermissionService.updateById(permission);
        if (result) {
            sysPermissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }

    @ApiOperation(value = "删除权限")
    @DeleteMapping("/{ids}")
    public Result deletePermissions(
            @ApiParam("权限ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean status = sysPermissionService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.judge(status);
    }
}
