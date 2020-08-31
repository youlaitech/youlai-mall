package com.youlai.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.entity.SysMenu;
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
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "username", value = "菜单名", paramType = "query", dataType = "String"),
    })
    @GetMapping
    public Result list(Integer page, Integer limit, String name) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<SysMenu>()
                .like(StrUtil.isNotBlank(name), SysMenu::getName, name)
                .orderByDesc(SysMenu::getUpdateTime)
                .orderByDesc(SysMenu::getCreateTime);

        if (page != null && limit != null) {
            Page<SysMenu> result = iSysMenuService.page(new Page<>(page, limit) ,queryWrapper);

            return PageResult.success(result.getRecords(), result.getTotal());
        } else if (limit != null) {
            queryWrapper.last("LIMIT " + limit);
        }
        List<SysMenu> list = iSysMenuService.list(queryWrapper);
        return Result.success(list);
    }

    @ApiOperation(value = "菜单详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "菜单id", required = true, paramType = "path", dataType = "Long")
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
            @ApiImplicitParam(name = "id", value = "菜单id", required = true, paramType = "path", dataType = "Long"),
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
    @ApiImplicitParam(name = "ids[]", value = "id集合", required = true, paramType = "query", allowMultiple = true, dataType = "Long")
    @DeleteMapping
    public Result delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iSysMenuService.removeByIds(ids);
        return Result.status(status);
    }

}
