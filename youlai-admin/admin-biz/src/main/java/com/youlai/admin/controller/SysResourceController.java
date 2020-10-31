package com.youlai.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.entity.SysResource;
import com.youlai.admin.entity.SysRoleResource;
import com.youlai.admin.service.ISysResourceService;
import com.youlai.admin.service.ISysRoleResourceService;
import com.youlai.common.core.result.PageResult;
import com.youlai.common.core.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api(tags = "资源接口")
@RestController
@RequestMapping("/resources")
@Slf4j
@AllArgsConstructor
public class SysResourceController {

    private ISysResourceService iSysResourceService;

    private ISysRoleResourceService iSysRoleResourceService;

    @ApiOperation(value = "列表分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "name", value = "资源名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "url", value = "资源路径", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "mode", value = "查询模式: 1-table数据 2-树形数据", defaultValue = "1", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "roleId", value = "角色ID", paramType = "query", dataType = "Integer")
    })
    @GetMapping
    public Result list(Integer page, Integer limit, String name, String url,
                       @RequestParam(required = false,defaultValue = "1") Integer mode,  // 默认table数据
                       Integer roleId) {

        LambdaQueryWrapper<SysResource> baseQuery = new LambdaQueryWrapper<SysResource>()
                .like(StrUtil.isNotBlank(name), SysResource::getName, name)
                .like(StrUtil.isNotBlank(url), SysResource::getUrl, url)
                .orderByDesc(SysResource::getGmtModified)
                .orderByDesc(SysResource::getGmtCreate);
        List list;
        if (mode.equals(2)) { // 树形数据
            list = iSysResourceService.listForTreeSelect(baseQuery);
            if (roleId != null) {
                Map<String, Object> map = new HashMap<>();
                map.put("resources", list);
                List<Integer> checkedKeys;
                checkedKeys = iSysRoleResourceService.list(new LambdaQueryWrapper<SysRoleResource>()
                        .eq(SysRoleResource::getRoleId, roleId))
                        .stream()
                        .map(item -> item.getResourceId())
                        .collect(Collectors.toList());
                map.put("checkedKeys", checkedKeys);
                return Result.success(map);
            }

        } else {
            if (page != null && limit != null) {
                Page<SysResource> result = iSysResourceService.page(new Page<>(page, limit), baseQuery);
                return PageResult.success(result.getRecords(), result.getTotal());
            } else if (limit != null) {
                baseQuery.last("LIMIT " + limit);
            }
            list = iSysResourceService.list(baseQuery);
        }


        return Result.success(list);
    }

    @ApiOperation(value = "资源详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "资源id", required = true, paramType = "path", dataType = "Integer")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        SysResource sysRole = iSysResourceService.getById(id);
        return Result.success(sysRole);
    }

    @ApiOperation(value = "新增资源", httpMethod = "POST")
    @ApiImplicitParam(name = "resource", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysResource")
    @PostMapping
    public Result add(@RequestBody SysResource resource) {
        boolean status = iSysResourceService.save(resource);
        return Result.status(status);
    }

    @ApiOperation(value = "修改资源", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "资源id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "resource", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysResource")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Integer id,
            @RequestBody SysResource resource) {
        boolean status = iSysResourceService.updateById(resource);
        return Result.status(status);
    }

    @ApiOperation(value = "删除资源", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "id集合", required = true, paramType = "query", allowMultiple = true, dataType = "Integer")
    @DeleteMapping
    public Result delete(@RequestParam(value = "ids") List<Integer> ids) {
        boolean status = iSysResourceService.removeByIds(ids);
        return Result.status(status);
    }
}
