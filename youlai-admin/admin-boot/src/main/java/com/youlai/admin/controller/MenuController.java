package com.youlai.admin.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.youlai.admin.pojo.entity.SysMenu;
import com.youlai.admin.pojo.vo.MenuVO;
import com.youlai.admin.pojo.vo.RouteVO;
import com.youlai.admin.pojo.vo.SelectVO;
import com.youlai.admin.pojo.vo.TreeSelectVO;
import com.youlai.admin.service.ISysMenuService;
import com.youlai.admin.service.ISysPermissionService;
import com.youlai.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 菜单控制器
 *
 * @author <a href="mailto:xianrui0365@163.com">xianrui</a>
 * @date 2020-11-06
 */
@Api(tags = "菜单接口")
@RestController
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
@Slf4j
public class MenuController {

    private final ISysMenuService menuService;
    private final ISysPermissionService permissionService;

    @ApiOperation(value = "菜单表格（Table）层级列表")
    @ApiImplicitParam(name = "name", value = "菜单名称", paramType = "query", dataType = "String")
    @GetMapping("/table")
    public Result getTableList(String name) {
        List<MenuVO> menuList = menuService.listTable(name);
        return Result.success(menuList);
    }


    @ApiOperation(value = "菜单下拉（Select）层级列表")
    @GetMapping("/select")
    public Result getSelectList() {
        List<SelectVO> menuList = menuService.listSelect();
        return Result.success(menuList);
    }

    @ApiOperation(value = "菜单下拉（TreeSelect）层级列表")
    @GetMapping("/tree-select")
    public Result getTreeSelectList() {
        List<TreeSelectVO> menuList = menuService.listTreeSelect();
        return Result.success(menuList);
    }

    @ApiOperation(value = "菜单路由（Route）层级列表")
    @GetMapping("/route")
    public Result getRouteList() {
        List<RouteVO> routeList = menuService.listRoute();
        return Result.success(routeList);
    }


    @ApiOperation(value = "菜单详情")
    @ApiImplicitParam(name = "id", value = "菜单id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        SysMenu menu = menuService.getById(id);
        return Result.success(menu);
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping
    @CacheEvict(cacheNames = "system",key = "'routeList'")
    public Result add(@RequestBody SysMenu menu) {
        boolean result = menuService.save(menu);
        if(result){
            permissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping(value = "/{id}")
    @CacheEvict(cacheNames = "system",key = "'routeList'")
    public Result update(
            @PathVariable Long id,
            @RequestBody SysMenu menu) {
        boolean result = menuService.updateById(menu);
        if(result){
            permissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }

    @ApiOperation(value = "删除菜单")
    @ApiImplicitParam(name = "ids", value = "id集合字符串，以,分割", required = true, paramType = "query", dataType = "String")
    @DeleteMapping("/{ids}")
    @CacheEvict(cacheNames = "system",key = "'routeList'")
    public Result delete(@PathVariable("ids") String ids) {
        boolean result = menuService.removeByIds(Arrays.asList(ids.split(",")));
        if(result){
            permissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }

    @ApiOperation(value = "选择性修改菜单")
    @PatchMapping(value = "/{id}")
    @CacheEvict(cacheNames = "system",key = "'routeList'")
    public Result patch(@PathVariable Integer id, @RequestBody SysMenu menu) {
        LambdaUpdateWrapper<SysMenu> updateWrapper = new LambdaUpdateWrapper<SysMenu>().eq(SysMenu::getId, id);
        updateWrapper.set(menu.getVisible() != null, SysMenu::getVisible, menu.getVisible());
        boolean result = menuService.update(updateWrapper);
        if(result){
            permissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }
}
