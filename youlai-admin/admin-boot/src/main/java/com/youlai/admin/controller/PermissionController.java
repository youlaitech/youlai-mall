package com.youlai.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.pojo.entity.SysPermission;
import com.youlai.admin.service.ISysPermissionService;
import com.youlai.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Api(tags = "权限接口")
@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final ISysPermissionService iSysPermissionService;

    @ApiOperation(value = "列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", defaultValue = "1", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", defaultValue = "10", value = "每页数量", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "name", value = "权限名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "menuId", value = "菜单ID", paramType = "query", dataType = "Long")
    })
    @GetMapping("/page")
    public Result pageList( Integer page,Integer limit, String name,  Long menuId) {
        IPage<SysPermission> result = iSysPermissionService.list(
                new Page<>(page, limit),
                new SysPermission()
                        .setMenuId(menuId)
                        .setName(name)
        );
        return Result.success(result.getRecords(), result.getTotal());
    }


    @ApiOperation(value = "权限列表")
    @ApiImplicitParam(name = "menuId", value = "菜单ID", paramType = "query", dataType = "Long")
    @GetMapping
    public Result list( Long menuId) {
        List<SysPermission> list = iSysPermissionService.list(new LambdaQueryWrapper<SysPermission>()
                .eq(SysPermission::getMenuId, menuId));
        return Result.success(list);
    }

    @ApiOperation(value = "权限详情")
    @ApiImplicitParam(name = "id", value = "权限ID", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        SysPermission permission = iSysPermissionService.getById(id);
        return Result.success(permission);
    }

    @ApiOperation(value = "新增权限")
    @ApiImplicitParam(name = "permission", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysPermission")
    @PostMapping
    public Result add(@RequestBody SysPermission permission) {
        boolean result = iSysPermissionService.save(permission);
        if (result) {
            iSysPermissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }

    @ApiOperation(value = "修改权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "权限id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "permission", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysPermission")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Long id,
            @RequestBody SysPermission permission) {
        boolean result = iSysPermissionService.updateById(permission);
        if (result) {
            iSysPermissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }

    @ApiOperation(value = "删除权限")
    @ApiImplicitParam(name = "ids", value = "id集合", required = true, paramType = "query", dataType = "Long")
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable String ids) {
        boolean status = iSysPermissionService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.judge(status);
    }
}
