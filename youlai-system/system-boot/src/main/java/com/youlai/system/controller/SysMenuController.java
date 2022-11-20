package com.youlai.system.controller;

import com.youlai.common.result.Result;
import com.youlai.common.web.model.Option;
import com.youlai.system.pojo.entity.SysMenu;
import com.youlai.system.pojo.query.MenuQuery;
import com.youlai.system.service.SysMenuService;
import com.youlai.system.pojo.vo.menu.MenuVO;
import com.youlai.system.pojo.vo.menu.ResourceVO;
import com.youlai.system.pojo.vo.menu.RouteVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 菜单控制器
 *
 * @author haoxr
 * @date 2020/11/06
 */
@Api(tags = "菜单接口")
@RestController
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
@Slf4j
public class SysMenuController {
    private final SysMenuService menuService;
    @ApiOperation(value = "资源(菜单+权限)列表")
    @GetMapping("/resources")
    public Result<List<ResourceVO>> listResources() {
        List<ResourceVO> resources = menuService.listResources();
        return Result.success(resources);
    }

    @ApiOperation(value = "菜单列表")
    @GetMapping
    public Result listMenus(MenuQuery queryParams) {
        List<MenuVO> menuList = menuService.listMenus(queryParams);
        return Result.success(menuList);
    }

    @ApiOperation(value = "菜单下拉列表")
    @GetMapping("/options")
    public Result listMenuOptions() {
        List<Option> menus = menuService.listMenuOptions();
        return Result.success(menus);
    }

    @ApiOperation(value = "路由列表")
    @GetMapping("/routes")
    public Result listRoutes() {
        List<RouteVO> routeList = menuService.listRoutes();
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
            @RequestBody SysMenu menu
    ) {
        boolean result = menuService.saveMenu(menu);
        return Result.judge(result);
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/{ids}")
    @CacheEvict(cacheNames = "system", key = "'routes'")
    public Result deleteMenus(
            @ApiParam("菜单ID，多个以英文(,)分割") @PathVariable("ids") String ids
    ) {
        boolean result = menuService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.judge(result);
    }

    @ApiOperation(value = "修改菜单显示状态")
    @PatchMapping("/{menuId}")
    public Result updateMenuVisible(
            @ApiParam(value = "菜单ID") @PathVariable Long menuId,
            @ApiParam(value = "显示状态(1:显示;0:隐藏)") Integer visible

    ) {
        boolean result =menuService.updateMenuVisible(menuId, visible);
        return Result.judge(result);
    }
}

