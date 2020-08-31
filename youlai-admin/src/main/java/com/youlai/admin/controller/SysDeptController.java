package com.youlai.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.entity.SysDept;
import com.youlai.admin.service.ISysDeptService;
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

@Api(tags = "部门接口")
@RestController
@RequestMapping("/depts")
@Slf4j
public class SysDeptController {

    @Autowired
    private ISysDeptService iSysDeptService;

    @ApiOperation(value = "列表分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "username", value = "部门名", paramType = "query", dataType = "String"),
    })
    @GetMapping
    public Result list(Integer page, Integer limit, String name) {
        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<SysDept>()
                .like(StrUtil.isNotBlank(name), SysDept::getName, name)
                .orderByDesc(SysDept::getUpdateTime)
                .orderByDesc(SysDept::getCreateTime);

        if (page != null && limit != null) {
            Page<SysDept> result = iSysDeptService.page(new Page<>(page, limit) ,queryWrapper);

            return PageResult.success(result.getRecords(), result.getTotal());
        } else if (limit != null) {
            queryWrapper.last("LIMIT " + limit);
        }
        List<SysDept> list = iSysDeptService.list(queryWrapper);
        return Result.success(list);
    }

    @ApiOperation(value = "部门详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "部门id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        SysDept sysDept = iSysDeptService.getById(id);
        return Result.success(sysDept);
    }

    @ApiOperation(value = "新增部门", httpMethod = "POST")
    @ApiImplicitParam(name = "sysDept", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysDept")
    @PostMapping
    public Result add(@RequestBody SysDept sysDept) {
        boolean status = iSysDeptService.save(sysDept);
        return Result.status(status);
    }

    @ApiOperation(value = "修改部门", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "部门id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "sysDept", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysDept")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Long id,
            @RequestBody SysDept sysDept) {
        sysDept.setUpdateTime(new Date());
        boolean status = iSysDeptService.updateById(sysDept);
        return Result.status(status);
    }

    @ApiOperation(value = "删除部门", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids[]", value = "id集合", required = true, paramType = "query", allowMultiple = true, dataType = "Long")
    @DeleteMapping
    public Result delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iSysDeptService.removeByIds(ids);
        return Result.status(status);
    }

}
