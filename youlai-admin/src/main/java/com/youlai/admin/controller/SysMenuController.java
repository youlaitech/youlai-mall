package com.youlai.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.common.AdminConstant;
import com.youlai.admin.domain.entity.SysMenu;
import com.youlai.admin.domain.entity.SysMenu;
import com.youlai.admin.domain.entity.SysMenu;
import com.youlai.admin.domain.entity.SysRoleMenu;
import com.youlai.admin.domain.vo.TreeSelectVO;
import com.youlai.admin.service.ISysMenuService;
import com.youlai.admin.service.ISysRoleMenuService;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Api(tags = "菜单接口")
@RestController
@RequestMapping("/menus")
@Slf4j
@AllArgsConstructor
public class SysMenuController {

    private ISysMenuService iSysMenuService;
    private ISysRoleMenuService iSysRoleMenuService;

    @ApiOperation(value = "菜单列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "菜单名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "roleId", value = "角色ID", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "mode", value = "查询模式: 1-表格数据 2-树形数据 3-菜单路由", paramType = "query", dataType = "Integer")
    })
    @GetMapping
    public Result list(String name, Integer roleId, Integer mode) {
        LambdaQueryWrapper<SysMenu> baseQuery = new LambdaQueryWrapper<SysMenu>()
                .orderByAsc(SysMenu::getSort)
                .orderByDesc(SysMenu::getUpdateTime)
                .orderByDesc(SysMenu::getCreateTime);
        List list;
        if (mode.equals(1)) { // 表格数据
            baseQuery = baseQuery.like(StrUtil.isNotBlank(name), SysMenu::getName, name);
            list = iSysMenuService.listForTableData(baseQuery);
        } else if (mode.equals(2)) { // 树形数据
            list = iSysMenuService.listForTreeSelect(baseQuery);
            if (roleId != null) { // 菜单树形 + 角色权限
                Map<String, Object> map = new HashMap<>();
                map.put("menus", list);
                List<Integer> checkedKeys;
                if (roleId.equals(AdminConstant.ROOT_ROLE_ID)) { // 超级管理员拥有所有的菜单权限
                    checkedKeys = (List<Integer>) list.stream().map(item -> ((TreeSelectVO) item).getId()).collect(Collectors.toList());
                } else {
                    checkedKeys = iSysRoleMenuService.list(new LambdaQueryWrapper<SysRoleMenu>()
                            .eq(SysRoleMenu::getRoleId, roleId))
                            .stream()
                            .map(item -> item.getMenuId())
                            .collect(Collectors.toList());
                }
                map.put("checkedKeys", checkedKeys);
                return Result.success(map);
            }
        } else if (mode.equals(3)) {
            list = iSysMenuService.listForRouter();
        } else {
            list = iSysMenuService.list(baseQuery);
        }
        return Result.success(list);
    }

    @ApiOperation(value = "菜单详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "菜单id", required = true, paramType = "path", dataType = "Integer")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        SysMenu menu = iSysMenuService.getById(id);
        return Result.success(menu);
    }

    @ApiOperation(value = "新增菜单", httpMethod = "POST")
    @ApiImplicitParam(name = "menu", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysMenu")
    @PostMapping
    public Result add(@RequestBody SysMenu menu) {
        boolean status = iSysMenuService.save(menu);
        return Result.status(status);
    }

    @ApiOperation(value = "修改菜单", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "菜单id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "menu", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysMenu")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Integer id,
            @RequestBody SysMenu menu) {
        boolean status = iSysMenuService.updateById(menu);
        return Result.status(status);
    }

    @ApiOperation(value = "删除菜单", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids[]", value = "id集合", required = true, paramType = "query", allowMultiple = true, dataType = "Integer")
    @DeleteMapping
    public Result delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iSysMenuService.removeByIds(ids);
        return Result.status(status);
    }

    @ApiOperation(value = "修改菜单【局部更新】", httpMethod = "PATCH")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "menu", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysMenu")
    })
    @PatchMapping(value = "/{id}")
    public Result patch(@PathVariable Integer id, @RequestBody SysMenu menu) {
        LambdaUpdateWrapper<SysMenu> luw = new LambdaUpdateWrapper<SysMenu>().eq(SysMenu::getId, id);
        if (menu.getStatus() != null) { // 状态更新
            luw.set(SysMenu::getStatus, menu.getStatus());
        } else {
            return Result.success();
        }
        boolean update = iSysMenuService.update(luw);
        return Result.success(update);
    }
}
