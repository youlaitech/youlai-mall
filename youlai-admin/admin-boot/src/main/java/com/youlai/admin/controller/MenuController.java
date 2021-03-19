package com.youlai.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.youlai.admin.pojo.entity.SysMenu;
import com.youlai.admin.service.ISysMenuService;
import com.youlai.admin.service.ISysRoleMenuService;
import com.youlai.common.enums.QueryModeEnum;
import com.youlai.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author haoxr
 * @date 2020-11-06
 */
@Api(tags = "菜单接口")
@RestController
@RequestMapping("/api.admin/v1/menus")
@Slf4j
@AllArgsConstructor
public class MenuController {

    private ISysMenuService iSysMenuService;
    private ISysRoleMenuService iSysRoleMenuService;

    @ApiOperation(value = "菜单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "菜单名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "roleId", value = "角色ID", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "queryMode", value = "查询模式", paramType = "query", dataType = "QueryModeEnum")
    })
    @GetMapping
    public Result list(String queryMode, String name, Long roleId) {

        QueryModeEnum queryModeEnum = QueryModeEnum.getValue(queryMode);

        LambdaQueryWrapper<SysMenu> baseQuery = new LambdaQueryWrapper<SysMenu>()
                .orderByAsc(SysMenu::getSort)
                .orderByDesc(SysMenu::getGmtModified)
                .orderByDesc(SysMenu::getGmtCreate);
        List list;
        switch (queryModeEnum) {
            case LIST:
                baseQuery = baseQuery.like(StrUtil.isNotBlank(name), SysMenu::getName, name);
                list = iSysMenuService.listMenuVO(baseQuery);
                break;
            case TREE:
                list = iSysMenuService.listTreeVO(baseQuery);
                break;
            case ROUTER:
                list = iSysMenuService.listRouterVO();
                break;
            default:
                list = iSysMenuService.list(baseQuery);
                break;
        }

        return Result.success(list);
    }

    @ApiOperation(value = "菜单详情")
    @ApiImplicitParam(name = "id", value = "菜单id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        SysMenu menu = iSysMenuService.getById(id);
        return Result.success(menu);
    }

    @ApiOperation(value = "新增菜单")
    @ApiImplicitParam(name = "menu", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysMenu")
    @PostMapping
    public Result add(@RequestBody SysMenu menu) {
        boolean status = iSysMenuService.save(menu);
        return Result.judge(status);
    }

    @ApiOperation(value = "修改菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "菜单id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "menu", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysMenu")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Integer id,
            @RequestBody SysMenu menu) {
        boolean status = iSysMenuService.updateById(menu);
        return Result.judge(status);
    }

    @ApiOperation(value = "删除菜单")
    @ApiImplicitParam(name = "ids", value = "id集合字符串，以,分割", required = true, paramType = "query", dataType = "String")
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable("ids") String ids) {
        boolean status = iSysMenuService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.judge(status);
    }

    @ApiOperation(value = "修改菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "menu", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysMenu")
    })
    @PatchMapping(value = "/{id}")
    public Result patch(@PathVariable Integer id, @RequestBody SysMenu menu) {
        LambdaUpdateWrapper<SysMenu> updateWrapper = new LambdaUpdateWrapper<SysMenu>().eq(SysMenu::getId, id);
        updateWrapper.set(menu.getVisible() != null, SysMenu::getVisible, menu.getVisible());
        boolean result = iSysMenuService.update(updateWrapper);
        return Result.judge(result);
    }
}
