package com.youlai.admin.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.pojo.entity.SysDict;
import com.youlai.admin.pojo.entity.SysDictItem;
import com.youlai.admin.service.ISysDictItemService;
import com.youlai.admin.service.ISysDictService;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "字典接口")
@RestController
@RequestMapping("/api/v1/dicts")
@RequiredArgsConstructor
public class SysDictController {

    private final ISysDictService iSysDictService;
    private final ISysDictItemService iSysDictItemService;

    @ApiOperation(value = "列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "name", value = "字典名称", paramType = "query", dataType = "String"),
    })
    @GetMapping("/page")
    public PageResult<SysDict> list(Long pageNum, Long pageSize, String name) {
        Page<SysDict> result = iSysDictService.page(new Page<>(pageNum, pageSize), new LambdaQueryWrapper<SysDict>()
                .like(StrUtil.isNotBlank(name), SysDict::getName, StrUtil.trimToNull(name))
                .orderByDesc(SysDict::getGmtModified)
                .orderByDesc(SysDict::getGmtCreate));
        return PageResult.success(result);
    }

    @ApiOperation(value = "字典列表")
    @GetMapping
    public Result list() {
        List<SysDict> list = iSysDictService.list(new LambdaQueryWrapper<SysDict>()
                .orderByDesc(SysDict::getGmtModified)
                .orderByDesc(SysDict::getGmtCreate));
        return Result.success(list);
    }


    @ApiOperation(value = "字典详情")
    @ApiImplicitParam(name = "id", value = "字典id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        SysDict dict = iSysDictService.getById(id);
        return Result.success(dict);
    }

    @ApiOperation(value = "新增字典")
    @ApiImplicitParam(name = "dictItem", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysDictItem")
    @PostMapping
    public Result add(@RequestBody SysDict dict) {
        boolean status = iSysDictService.save(dict);
        return Result.judge(status);
    }

    @ApiOperation(value = "修改字典")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "字典id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "dictItem", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysDictItem")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Long id,
            @RequestBody SysDict dict) {

        boolean status = iSysDictService.updateById(dict);
        if (status) {
            SysDict dbDict = iSysDictService.getById(id);
            // 字典code更新，同步更新字典项code
            if (!StrUtil.equals(dbDict.getCode(), dict.getCode())) {
                iSysDictItemService.update(new LambdaUpdateWrapper<SysDictItem>().eq(SysDictItem::getDictCode, dbDict.getCode())
                        .set(SysDictItem::getDictCode, dict.getCode()));
            }
        }
        return Result.judge(status);
    }

    @ApiOperation(value = "删除字典")
    @ApiImplicitParam(name = "ids", value = "以,分割拼接字符串", required = true, paramType = "query", dataType = "String")
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        List<String> codeList = iSysDictService.listByIds(idList).stream().map(item -> item.getCode()).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(codeList)) {
            int count = iSysDictItemService.count(new LambdaQueryWrapper<SysDictItem>().in(SysDictItem::getDictCode, codeList));
            Assert.isTrue(count == 0, "删除字典失败，请先删除关联字典数据");
        }
        boolean status = iSysDictService.removeByIds(idList);
        return Result.judge(status);
    }

    @ApiOperation(value = "选择性更新字典")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "dictItem", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysDictItem")
    })
    @PatchMapping(value = "/{id}")
    public Result patch(@PathVariable Long id, @RequestBody SysDict dict) {
        LambdaUpdateWrapper<SysDict> updateWrapper = new LambdaUpdateWrapper<SysDict>().eq(SysDict::getId, id);
        updateWrapper.set(dict.getStatus() != null, SysDict::getStatus, dict.getStatus());
        boolean update = iSysDictService.update(updateWrapper);
        return Result.success(update);
    }
}
