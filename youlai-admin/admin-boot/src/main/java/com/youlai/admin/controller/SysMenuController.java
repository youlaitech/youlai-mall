package com.youlai.admin.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.youlai.admin.pojo.entity.SysMenu;
import com.youlai.admin.pojo.vo.menu.MenuTableVO;
import com.youlai.admin.pojo.vo.menu.NextRouteVO;
import com.youlai.admin.service.ISysMenuService;
import com.youlai.admin.service.ISysPermissionService;
import com.youlai.common.result.Result;
import com.youlai.common.web.vo.OptionVO;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 菜单路由控制器
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2020-11-06
 */
@Api(tags = "菜单接口")
@RestController
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
@Slf4j
public class SysMenuController {

    private final ISysMenuService menuService;
    private final ISysPermissionService permissionService;

    @ApiOperation(value = "菜单表格(Table)列表")
    @GetMapping("/table")
    public Result listTableMenus(
            @ApiParam(value = "菜单名称", type = "query") String name
    ) {
        List<MenuTableVO> menuList = menuService.listTableMenus(name);
        return Result.success(menuList);
    }

    @ApiOperation(value = "菜单下拉(Select)列表")
    @GetMapping("/select")
    public Result listSelectMenus() {
        List<OptionVO> menuList = menuService.listSelectMenus();
        return Result.success(menuList);
    }

    @ApiOperation(value = "菜单路由(Route)列表")
    @GetMapping("/route")
    public Result getRouteList() {
        List<NextRouteVO> routeList = menuService.listNextRoutes();
        return Result.success(routeList);
    }

    @ApiOperation(value = "菜单详情")
    @GetMapping("/{id}")
    public Result detail(
            @ApiParam(value = "菜单ID") @PathVariable Long id
    ) {
        SysMenu menu = menuService.getById(id);
        return Result.success(menu);
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping
    @CacheEvict(cacheNames = "system", key = "'routes'")
    public Result addMenu(@RequestBody SysMenu menu) {
        boolean result = menuService.saveMenu(menu);
        return Result.judge(result);
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping(value = "/{id}")
    @CacheEvict(cacheNames = "system", key = "'routes'")
    public Result updateMenu(
            @ApiParam("菜单ID") @PathVariable Long id,
            @RequestBody SysMenu menu
    ) {
        boolean result = menuService.updateMenu(menu);
        return Result.judge(result);
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/{ids}")
    @CacheEvict(cacheNames = "system", key = "'routes'")
    public Result delete(
            @ApiParam("菜单ID，多个以英文(,)分割") @PathVariable("ids") String ids) {
        boolean result = menuService.removeByIds(Arrays.asList(ids.split(",")));
        if (result) {
            permissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }

    @ApiOperation(value = "选择性修改菜单")
    @PatchMapping(value = "/{id}")
    @CacheEvict(cacheNames = "system", key = "'routes'")
    public Result patch(@PathVariable Integer id, @RequestBody SysMenu menu) {
        LambdaUpdateWrapper<SysMenu> updateWrapper = new LambdaUpdateWrapper<SysMenu>().eq(SysMenu::getId, id);
        updateWrapper.set(menu.getVisible() != null, SysMenu::getVisible, menu.getVisible());
        boolean result = menuService.update(updateWrapper);
        if (result) {
            permissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }
}
