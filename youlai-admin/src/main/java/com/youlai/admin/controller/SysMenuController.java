package com.youlai.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.domain.entity.SysMenu;
import com.youlai.admin.domain.entity.SysMenu;
import com.youlai.admin.domain.entity.SysMenu;
import com.youlai.admin.service.ISysMenuService;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Api(tags = "菜单接口")
@RestController
@RequestMapping("/menus")
@Slf4j
public class SysMenuController {

    @Autowired
    private ISysMenuService iSysMenuService;

    @ApiOperation(value = "列表分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "菜单名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "mode", value = "查询模式: 1-表格数据 2-树形下拉", paramType = "query", dataType = "Integer")
    })
    @GetMapping
    public Result list(String name,Integer mode) {
        LambdaQueryWrapper<SysMenu> baseQuery = new LambdaQueryWrapper<SysMenu>()
                .orderByAsc(SysMenu::getSort)
                .orderByDesc(SysMenu::getUpdateTime)
                .orderByDesc(SysMenu::getCreateTime);
        List list;
        if (mode.equals(1)) { // 表格数据
            baseQuery = baseQuery.like(StrUtil.isNotBlank(name), SysMenu::getName, name);
            list = iSysMenuService.listForTableData(baseQuery);
        } else if (mode.equals(2)) { // tree-select 树形下拉数据
            list = iSysMenuService.listForTreeSelect(baseQuery);
        } else {
            list = iSysMenuService.list(baseQuery);
        }
        return Result.success(list);
    }

    @ApiOperation(value = "菜单详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "菜单id", required = true, paramType = "path", dataType = "Integer")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        SysMenu sysMenu = iSysMenuService.getById(id);
        return Result.success(sysMenu);
    }

    @ApiOperation(value = "新增菜单", httpMethod = "POST")
    @ApiImplicitParam(name = "sysMenu", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysMenu")
    @PostMapping
    public Result add(@RequestBody SysMenu sysMenu) {
        boolean status = iSysMenuService.save(sysMenu);
        return Result.status(status);
    }

    @ApiOperation(value = "修改菜单", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "菜单id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "sysMenu", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysMenu")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Long id,
            @RequestBody SysMenu sysMenu) {
        sysMenu.setUpdateTime(new Date());
        boolean status = iSysMenuService.updateById(sysMenu);
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
        }  else {
            return Result.success();
        }
        boolean update = iSysMenuService.update(luw);
        return Result.success(update);
    }
}
