package com.youlai.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.youlai.admin.pojo.SysMenu;
import com.youlai.admin.pojo.SysRoleMenu;
import com.youlai.admin.service.ISysMenuService;
import com.youlai.admin.service.ISysRoleMenuService;
import com.youlai.common.core.enums.QueryModeEnum;
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

/**
 * @author haoxr
 * @date 2020-11-06
 */
@Api(tags = "菜单接口")
@RestController
@RequestMapping("/menus")
@Slf4j
@AllArgsConstructor
public class MenuController {

    private ISysMenuService iSysMenuService;
    private ISysRoleMenuService iSysRoleMenuService;

    @ApiOperation(value = "菜单列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "菜单名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "roleId", value = "角色ID", paramType = "query", dataType = "Integer"),
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
            case TREE:
                baseQuery = baseQuery.like(StrUtil.isNotBlank(name), SysMenu::getName, name);
                list = iSysMenuService.listForTree(baseQuery);
                break;
            case TREESELECT:
                list = iSysMenuService.listForTreeSelect(baseQuery);
                if (roleId != null) { // 菜单树形 + 角色权限
                    Map<String, Object> map = new HashMap<>();
                    map.put("menus", list);
                    List<Long> checkedKeys = iSysRoleMenuService.list(new LambdaQueryWrapper<SysRoleMenu>()
                            .eq(SysRoleMenu::getRoleId, roleId))
                            .stream()
                            .map(item -> item.getMenuId())
                            .collect(Collectors.toList());
                    map.put("checkedKeys", checkedKeys);
                    return Result.success(map);
                }
                break;
            case ROUTER:
                list = iSysMenuService.listForRouter();
                break;
            default:
                list = iSysMenuService.list(baseQuery);
                break;
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
