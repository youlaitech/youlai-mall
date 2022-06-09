package com.youlai.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.pojo.form.DictItemForm;
import com.youlai.admin.pojo.query.DictItemPageQuery;
import com.youlai.admin.pojo.vo.dict.DictItemPageVO;
import com.youlai.admin.service.SysDictItemService;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "字典数据项")
@RestController
@RequestMapping("/api/v1/dict-items")
@RequiredArgsConstructor
public class SysDictItemController {

    private final SysDictItemService dictItemService;

    @ApiOperation(value = "字典数据分页列表")
    @GetMapping("/page_list")
    public PageResult<DictItemPageVO> listPageDictItems(DictItemPageQuery queryParams) {
        Page<DictItemPageVO> result = dictItemService.listPageDictItems(queryParams);
        return PageResult.success(result);
    }

    @ApiOperation(value = "字典数据项表单详情")
    @GetMapping("/{id}/form_detail")
    public Result<DictItemForm> getDictItemFormDetail(
            @ApiParam("字典ID") @PathVariable Long id
    ) {
        DictItemForm DictItemForm = dictItemService.getDictItemFormDetail(id);
        return Result.success(DictItemForm);
    }

    @ApiOperation(value = "新增字典数据项")
    @PostMapping
    public Result saveDictItem(@RequestBody DictItemForm DictItemForm) {
        boolean result = dictItemService.saveDictItem(DictItemForm);
        return Result.judge(result);
    }

    @ApiOperation(value = "修改字典数据项")
    @PutMapping("/{id}")
    public Result updateDict(@PathVariable Long id, @RequestBody DictItemForm DictItemForm) {
        boolean status = dictItemService.updateDictItem(id, DictItemForm);
        return Result.judge(status);
    }

    @ApiOperation(value = "删除字典")
    @DeleteMapping("/{ids}")
    public Result deleteDict(
            @ApiParam("字典ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = dictItemService.deleteDictItems(ids);
        return Result.judge(result);
    }

}
