package com.youlai.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.pojo.SysDictType;
import com.youlai.admin.service.ISysDictService;
import com.youlai.admin.service.ISysDictTypeService;
import com.youlai.common.core.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "字典类型接口")
@RestController
@RequestMapping("/dict-types")
@AllArgsConstructor
@Slf4j
public class DictTypeController {

    private ISysDictTypeService iSysDictTypeService;

    private ISysDictService iSysDictService;

    @ApiOperation(value = "列表分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "name", value = "类型名", paramType = "query", dataType = "String"),
    })
    @GetMapping
    public Result list(Integer page, Integer limit, String name) {
        LambdaQueryWrapper<SysDictType> queryWrapper = new LambdaQueryWrapper<SysDictType>()
                .like(StrUtil.isNotBlank(name), SysDictType::getName, StrUtil.trimToNull(name))
                .orderByDesc(SysDictType::getGmtModified)
                .orderByDesc(SysDictType::getGmtCreate);

        if (page != null && limit != null) {
            Page<SysDictType> result = iSysDictTypeService.page(new Page<>(page, limit) ,queryWrapper);
            return Result.success(result.getRecords(), result.getTotal());
        } else if (limit != null) {
            queryWrapper.last("LIMIT " + limit);
        }
        List<SysDictType> list = iSysDictTypeService.list(queryWrapper);
        return Result.success(list);
    }

    @ApiOperation(value = "字典类型详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "字典类型id", required = true, paramType = "path", dataType = "Integer")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        SysDictType dictType = iSysDictTypeService.getById(id);
        return Result.success(dictType);
    }

    @ApiOperation(value = "新增字典类型", httpMethod = "POST")
    @ApiImplicitParam(name = "dictType", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysDictType")
    @PostMapping
    public Result add(@RequestBody SysDictType dictType) {
        boolean status = iSysDictTypeService.save(dictType);
        return Result.status(status);
    }

    @ApiOperation(value = "修改字典类型", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "字典类型id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "dictType", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysDictType")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Integer id,
            @RequestBody SysDictType dictType) {
        SysDictType beforeDictType = iSysDictTypeService.getById(id);
        boolean status = iSysDictTypeService.updateById(dictType);
        if (!StrUtil.equals(beforeDictType.getCode(),dictType.getCode())){
            iSysDictService.updateTypeCode(beforeDictType.getCode(),dictType.getCode());
        }
        return Result.status(status);
    }

    @ApiOperation(value = "删除字典类型", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids[]", value = "id集合", required = true, paramType = "query", allowMultiple = true, dataType = "Integer")
    @DeleteMapping
    public Result delete(@RequestParam("ids") List<Long> ids) {
        Integer dictCount = iSysDictService.countByDictTypeIds(ids);
        if (dictCount != null && dictCount > 0){
            return Result.failed("删除字典类型失败，请先删除关联字典数据");
        }
        boolean status = iSysDictTypeService.removeByIds(ids);
        return Result.status(status);
    }

    @ApiOperation(value = "修改字典类型(部分更新)", httpMethod = "PATCH")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "dictType", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysDictType")
    })
    @PatchMapping(value = "/{id}")
    public Result patch(@PathVariable Integer id, @RequestBody SysDictType dictType) {
        LambdaUpdateWrapper<SysDictType> luw = new LambdaUpdateWrapper<SysDictType>().eq(SysDictType::getId, id);
        if (dictType.getStatus() != null) { // 状态更新
            luw.set(SysDictType::getStatus, dictType.getStatus());
        } else {
            return Result.success();
        }
        boolean update = iSysDictTypeService.update(luw);
        return Result.success(update);
    }
}
