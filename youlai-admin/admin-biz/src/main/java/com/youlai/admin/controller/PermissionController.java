package com.youlai.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youlai.admin.pojo.SysPermission;
import com.youlai.admin.pojo.SysRolePermission;
import com.youlai.admin.pojo.vo.PermissionVO;
import com.youlai.admin.service.ISysPermissionService;
import com.youlai.admin.service.ISysRolePermissionService;
import com.youlai.common.core.enums.QueryModeEnum;
import com.youlai.common.core.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "权限接口")
@RestController
@RequestMapping("/api.admin/v1/permissions")
@AllArgsConstructor
public class PermissionController {

    private ISysPermissionService iSysPermissionService;

    private ISysRolePermissionService iSysRolePermissionService;

    @ApiOperation(value = "列表分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryMode", paramType = "query", dataType = "QueryModeEnum"),
            @ApiImplicitParam(name = "name", value = "权限名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "permission", value = "权限标识", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "roleId", value = "角色ID", paramType = "query", dataType = "Long")
    })
    @GetMapping
    public Result list(
            String queryMode,
            String name,
            String permission,
            Long roleId) {
        QueryModeEnum queryModeEnum = QueryModeEnum.getValue(queryMode);
        LambdaQueryWrapper<SysPermission> baseQuery = new LambdaQueryWrapper<SysPermission>()
                .like(StrUtil.isNotBlank(name), SysPermission::getName, name)
                .like(StrUtil.isNotBlank(permission), SysPermission::getPermission, permission)
                .orderByDesc(SysPermission::getGmtModified)
                .orderByDesc(SysPermission::getGmtCreate);
        List list;
        switch (queryModeEnum) {
            case TREE:
                list = iSysPermissionService.listForTree(baseQuery);
                PermissionVO permissionVO = new PermissionVO();
                permissionVO.setPermissions(list);
                List<Long> checkedKeys = iSysRolePermissionService.list(
                        new LambdaQueryWrapper<SysRolePermission>()
                                .eq(SysRolePermission::getRoleId, roleId)
                ).stream().map(item -> item.getPermissionId()).collect(Collectors.toList());
                permissionVO.setCheckedKeys(checkedKeys);
                return Result.success(permissionVO);
            default:
                list = iSysPermissionService.list();
                break;
        }
        return Result.success(list);
    }

    @ApiOperation(value = "权限详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "权限ID", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        SysPermission permission = iSysPermissionService.getById(id);
        return Result.success(permission);
    }

    @ApiOperation(value = "新增权限", httpMethod = "POST")
    @ApiImplicitParam(name = "permission", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysPermission")
    @PostMapping
    public Result add(@RequestBody SysPermission permission) {
        boolean status = iSysPermissionService.save(permission);
        return Result.status(status);
    }

    @ApiOperation(value = "修改权限", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "权限id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "permission", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysPermission")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Long id,
            @RequestBody SysPermission permission) {
        boolean status = iSysPermissionService.updateById(permission);
        return Result.status(status);
    }

    @ApiOperation(value = "删除权限", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "id集合", required = true, paramType = "query", allowMultiple = true, dataType = "Integer")
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable String ids) {
        boolean status = iSysPermissionService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.status(status);
    }
}
