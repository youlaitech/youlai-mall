package com.youlai.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.system.pojo.form.DictItemForm;
import com.youlai.system.pojo.query.DictItemPageQuery;
import com.youlai.system.pojo.vo.dict.DictItemPageVO;
import com.youlai.system.service.SysDictItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "字典数据接口")
@RestController
@RequestMapping("/api/v1/dict/items")
@RequiredArgsConstructor
public class SysDictItemController {

    private final SysDictItemService dictItemService;

    @ApiOperation(value = "字典数据分页列表")
    @GetMapping("/pages")
    public PageResult<DictItemPageVO> listDictItemPages(
            DictItemPageQuery queryParams
    ) {
        Page<DictItemPageVO> result = dictItemService.listDictItemPages(queryParams);
        return PageResult.success(result);
    }

    @ApiOperation(value = "字典数据表单数据")
    @GetMapping("/{id}/form")
    public Result<DictItemForm> getDictItemForm(
            @ApiParam("字典ID") @PathVariable Long id
    ) {
        DictItemForm formData = dictItemService.getDictItemForm(id);
        return Result.success(formData);
    }

    @ApiOperation(value = "新增字典数据")
    @PostMapping
    public Result saveDictItem(
            @RequestBody DictItemForm DictItemForm
    ) {
        boolean result = dictItemService.saveDictItem(DictItemForm);
        return Result.judge(result);
    }

    @ApiOperation(value = "修改字典数据")
    @PutMapping("/{id}")
    public Result updateDictItem(
            @PathVariable Long id,
            @RequestBody DictItemForm DictItemForm
    ) {
        boolean status = dictItemService.updateDictItem(id, DictItemForm);
        return Result.judge(status);
    }

    @ApiOperation(value = "删除字典")
    @DeleteMapping("/{ids}")
    public Result deleteDictItems(
            @ApiParam("字典ID，多个以英文逗号(,)拼接") @PathVariable String ids
    ) {
        boolean result = dictItemService.deleteDictItems(ids);
        return Result.judge(result);
    }

}
