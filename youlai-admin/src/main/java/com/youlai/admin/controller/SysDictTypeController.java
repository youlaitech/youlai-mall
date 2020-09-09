package com.youlai.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.domain.entity.SysDictType;
import com.youlai.admin.service.ISysDictTypeService;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "字典类型接口")
@RestController
@RequestMapping("/dict-types")
@Slf4j
public class SysDictTypeController {


    @Autowired
    private ISysDictTypeService iSysDictTypeService;

    @ApiOperation(value = "列表分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "username", value = "字典类型名", paramType = "query", dataType = "String"),
    })
    @GetMapping
    public Result list(Integer page, Integer limit, String name) {
        LambdaQueryWrapper<SysDictType> queryWrapper = new LambdaQueryWrapper<SysDictType>()
                .like(StrUtil.isNotBlank(name), SysDictType::getName, name)
                .orderByDesc(SysDictType::getUpdateTime)
                .orderByDesc(SysDictType::getCreateTime);

        if (page != null && limit != null) {
            Page<SysDictType> result = iSysDictTypeService.page(new Page<>(page, limit) ,queryWrapper);

            return PageResult.success(result.getRecords(), result.getTotal());
        } else if (limit != null) {
            queryWrapper.last("LIMIT " + limit);
        }
        List<SysDictType> list = iSysDictTypeService.list(queryWrapper);
        return Result.success(list);
    }

    @ApiOperation(value = "字典类型详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "字典类型id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        SysDictType sysDictType = iSysDictTypeService.getById(id);
        return Result.success(sysDictType);
    }

    @ApiOperation(value = "新增字典类型", httpMethod = "POST")
    @ApiImplicitParam(name = "sysDictType", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysDictType")
    @PostMapping
    public Result add(@RequestBody SysDictType sysDictType) {
        boolean status = iSysDictTypeService.save(sysDictType);
        return Result.status(status);
    }

    @ApiOperation(value = "修改字典类型", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "字典类型id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "sysDictType", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysDictType")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Long id,
            @RequestBody SysDictType sysDictType) {
        boolean status = iSysDictTypeService.updateById(sysDictType);
        return Result.status(status);
    }

    @ApiOperation(value = "删除字典类型", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids[]", value = "id集合", required = true, paramType = "query", allowMultiple = true, dataType = "Long")
    @DeleteMapping
    public Result delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iSysDictTypeService.removeByIds(ids);
        return Result.status(status);
    }

}
