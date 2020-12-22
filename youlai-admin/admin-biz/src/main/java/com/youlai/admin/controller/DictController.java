package com.youlai.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.pojo.SysDict;
import com.youlai.admin.service.ISysDictService;
import com.youlai.common.core.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Api(tags = "字典接口")
@RestController
@RequestMapping("/dictionaries")
@Slf4j
@AllArgsConstructor
public class DictController {

    private ISysDictService iSysDictService;

    @ApiOperation(value = "列表分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", defaultValue = "1", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", defaultValue = "10", value = "每页数量", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "queryMode", defaultValue = "1", value = "查询模式：1-常规列表 2-表格列表", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "name", value = "字典名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "typeCode", value = "字典类型编码", paramType = "query", dataType = "String")
    })
    @GetMapping
    public Result list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "1") Integer queryMode,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String typeCode
    ) {

        if (queryMode.equals(2)) {
            IPage<SysDict> result = iSysDictService.list(new Page<>(page, limit), new SysDict().setName(name).setTypeCode(typeCode));
            return Result.success(result.getRecords(), result.getTotal());
        } else {
            List<SysDict> list = iSysDictService.list(new LambdaQueryWrapper<SysDict>()
                    .like(StrUtil.isNotBlank(name), SysDict::getName, name)
                    .eq(StrUtil.isNotBlank(typeCode), SysDict::getTypeCode, typeCode)
                    .select(SysDict::getName, SysDict::getValue)
                    .orderByAsc(SysDict::getSort)
            );
            return Result.success(list);
        }
    }

    @ApiOperation(value = "字典详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "字典id", required = true, paramType = "path", dataType = "Integer")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        SysDict dict = iSysDictService.getById(id);
        return Result.success(dict);
    }

    @ApiOperation(value = "新增字典", httpMethod = "POST")
    @ApiImplicitParam(name = "dict", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysDict")
    @PostMapping
    public Result add(@RequestBody SysDict dict) {
        boolean status = iSysDictService.save(dict);
        return Result.status(status);
    }

    @ApiOperation(value = "修改字典", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "字典id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "dict", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysDict")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Integer id,
            @RequestBody SysDict dict) {
        dict.setGmtModified(new Date());
        boolean status = iSysDictService.updateById(dict);
        return Result.status(status);
    }

    @ApiOperation(value = "删除字典", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids[]", value = "id集合", required = true, paramType = "query", allowMultiple = true, dataType = "Integer")
    @DeleteMapping
    public Result delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iSysDictService.removeByIds(ids);
        return Result.status(status);
    }


    @ApiOperation(value = "修改字典(部分更新)", httpMethod = "PATCH")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "dict", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysDict")
    })
    @PatchMapping(value = "/{id}")
    public Result patch(@PathVariable Integer id, @RequestBody SysDict dict) {
        LambdaUpdateWrapper<SysDict> luw = new LambdaUpdateWrapper<SysDict>().eq(SysDict::getId, id);
        if (dict.getStatus() != null) { // 状态更新
            luw.set(SysDict::getStatus, dict.getStatus());
        } else {
            return Result.success();
        }
        boolean update = iSysDictService.update(luw);
        return Result.success(update);
    }
}
