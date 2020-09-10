package com.youlai.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.domain.entity.SysDict;
import com.youlai.admin.service.ISysDictService;
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

@Api(tags = "字典接口")
@RestController
@RequestMapping("/dicts")
@Slf4j
public class SysDictController {


    @Autowired
    private ISysDictService iSysDictService;

    @ApiOperation(value = "列表分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "username", value = "字典名", paramType = "query", dataType = "String"),
    })
    @GetMapping
    public Result list(Integer page, Integer limit, String name) {
        LambdaQueryWrapper<SysDict> queryWrapper = new LambdaQueryWrapper<SysDict>()
                .like(StrUtil.isNotBlank(name), SysDict::getLabel, name)
                .orderByDesc(SysDict::getUpdateTime)
                .orderByDesc(SysDict::getCreateTime);

        if (page != null && limit != null) {
            Page<SysDict> result = iSysDictService.page(new Page<>(page, limit) ,queryWrapper);

            return PageResult.success(result.getRecords(), result.getTotal());
        } else if (limit != null) {
            queryWrapper.last("LIMIT " + limit);
        }
        List<SysDict> list = iSysDictService.list(queryWrapper);
        return Result.success(list);
    }

    @ApiOperation(value = "字典详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "字典id", required = true, paramType = "path", dataType = "Integer")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        SysDict sysDict = iSysDictService.getById(id);
        return Result.success(sysDict);
    }

    @ApiOperation(value = "新增字典", httpMethod = "POST")
    @ApiImplicitParam(name = "sysDict", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysDict")
    @PostMapping
    public Result add(@RequestBody SysDict sysDict) {
        boolean status = iSysDictService.save(sysDict);
        return Result.status(status);
    }

    @ApiOperation(value = "修改字典", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "字典id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "sysDict", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysDict")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Long id,
            @RequestBody SysDict sysDict) {
        sysDict.setUpdateTime(new Date());
        boolean status = iSysDictService.updateById(sysDict);
        return Result.status(status);
    }

    @ApiOperation(value = "删除字典", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids[]", value = "id集合", required = true, paramType = "query", allowMultiple = true, dataType = "Integer")
    @DeleteMapping
    public Result delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iSysDictService.removeByIds(ids);
        return Result.status(status);
    }

}
