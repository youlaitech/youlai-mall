package com.youlai.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.pojo.entity.SysPermission;
import com.youlai.admin.service.ISysPermissionService;
import com.youlai.common.enums.QueryModeEnum;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Api(tags = "权限接口")
@RestController
@RequestMapping("/api.admin/v1/permissions")
@AllArgsConstructor
public class PermissionController {

    private ISysPermissionService iSysPermissionService;

    @ApiOperation(value = "列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryMode", paramType = "query", dataType = "QueryModeEnum"),
            @ApiImplicitParam(name = "page", defaultValue = "1", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", defaultValue = "10", value = "每页数量", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "name", value = "权限名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "permission", value = "权限标识", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "权限类型", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "moduleId", value = "菜单ID", paramType = "query", dataType = "Long")
    })
    @GetMapping
    public Result list(
            String queryMode,
            Integer page,
            Integer limit,
            String name,
            String permission,
            Long moduleId,
            Integer type
    ) {
        QueryModeEnum queryModeEnum = QueryModeEnum.getValue(queryMode);

        switch (queryModeEnum) {
            case PAGE:
                IPage<SysPermission> result = iSysPermissionService.list(
                        new Page<>(page, limit),
                        new SysPermission()
                                .setPerm(permission)
                                .setModuleId(moduleId)
                                .setName(name)
                                .setType(type)
                );
                return Result.success(result.getRecords(), result.getTotal());

            default:
                return Result.failed(ResultCode.QUERY_MODE_IS_NULL);
        }
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
            iSysPermissionService.refreshPermissionRolesCache();
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
            iSysPermissionService.refreshPermissionRolesCache();
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
