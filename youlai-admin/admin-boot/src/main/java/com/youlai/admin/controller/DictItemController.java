package com.youlai.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.pojo.entity.SysDictItem;
import com.youlai.admin.service.ISysDictItemService;
import com.youlai.common.enums.QueryModeEnum;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Api(tags = "字典项接口")
@RestController
@RequestMapping("/api.admin/v1/dict_items")
@Slf4j
@AllArgsConstructor
public class DictItemController {

    private ISysDictItemService iSysDictItemService;

    @ApiOperation(value = "列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryMode", paramType = "query", dataType = "QueryModeEnum"),
            @ApiImplicitParam(name = "page", defaultValue = "1", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", defaultValue = "10", value = "每页数量", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "name", value = "字典名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "dictCode", value = "字典编码", paramType = "query", dataType = "String")
    })
    @GetMapping
    public Result list(
            String queryMode,
            Integer page,
            Integer limit,
            String name,
            String dictCode
    ) {
        QueryModeEnum queryModeEnum = QueryModeEnum.getValue(queryMode);
        switch (queryModeEnum) {
            case PAGE:
                IPage<SysDictItem> result = iSysDictItemService.list(new Page<>(page, limit), new SysDictItem().setName(name).setDictCode(dictCode));
                return Result.success(result.getRecords(), result.getTotal());
            case LIST:
                List<SysDictItem> list = iSysDictItemService.list(new LambdaQueryWrapper<SysDictItem>()
                        .like(StrUtil.isNotBlank(name), SysDictItem::getName, name)
                        .eq(StrUtil.isNotBlank(dictCode), SysDictItem::getDictCode, dictCode)
                        .select(SysDictItem::getName, SysDictItem::getValue)
                        .orderByAsc(SysDictItem::getSort)
                );
                return Result.success(list);
            default:
                return Result.failed(ResultCode.QUERY_MODE_IS_NULL);

        }
    }

    @ApiOperation(value = "字典项详情")
    @ApiImplicitParam(name = "id", value = "字典id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        SysDictItem dictItem = iSysDictItemService.getById(id);
        return Result.success(dictItem);
    }

    @ApiOperation(value = "新增字典项")
    @ApiImplicitParam(name = "dictItem", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysDictItem")
    @PostMapping
    public Result add(@RequestBody SysDictItem dictItem) {
        boolean status = iSysDictItemService.save(dictItem);
        return Result.judge(status);
    }

    @ApiOperation(value = "修改字典项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "字典id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "dictItem", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysDictItem")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Long id,
            @RequestBody SysDictItem dictItem) {
        dictItem.setGmtModified(new Date());
        boolean status = iSysDictItemService.updateById(dictItem);
        return Result.judge(status);
    }

    @ApiOperation(value = "删除字典数据")
    @ApiImplicitParam(name = "ids", value = "主键ID集合，以,分割拼接字符串", required = true, paramType = "query", dataType = "String")
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable String ids) {
        boolean status = iSysDictItemService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.judge(status);
    }


    @ApiOperation(value = "局部更新字典数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "dictItem", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysDictItem")
    })
    @PatchMapping(value = "/{id}")
    public Result patch(@PathVariable Integer id, @RequestBody SysDictItem dictItem) {
        LambdaUpdateWrapper<SysDictItem> updateWrapper = new LambdaUpdateWrapper<SysDictItem>().eq(SysDictItem::getId, id);
        updateWrapper.set(dictItem.getStatus() != null, SysDictItem::getStatus, dictItem.getStatus());
        boolean status = iSysDictItemService.update(updateWrapper);
        return Result.judge(status);
    }
}
