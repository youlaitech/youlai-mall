package com.youlai.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.pojo.form.DictTypeForm;
import com.youlai.admin.pojo.query.DictTypePageQuery;
import com.youlai.admin.pojo.vo.dict.DictTypePageVO;
import com.youlai.admin.service.SysDictTypeService;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "字典类型")
@RestController
@RequestMapping("/api/v1/dict-types")
@RequiredArgsConstructor
public class SysDictTypeController {

    private final SysDictTypeService dictTypeService;

    @ApiOperation(value = "字典类型分页列表")
    @GetMapping
    public PageResult<DictTypePageVO> listDictTypePages(DictTypePageQuery queryParams) {
        Page<DictTypePageVO> result = dictTypeService.listDictTypePages(queryParams);
        return PageResult.success(result);
    }

    @ApiOperation(value = "字典类型表单详情")
    @GetMapping("/{id}/form_data")
    public Result<DictTypeForm> getDictTypeFormData(
            @ApiParam("字典ID") @PathVariable Long id
    ) {
        DictTypeForm dictTypeForm = dictTypeService.getDictTypeFormData(id);
        return Result.success(dictTypeForm);
    }

    @ApiOperation(value = "新增字典类型")
    @PostMapping
    public Result saveDictType(@RequestBody DictTypeForm dictTypeForm) {
        boolean result = dictTypeService.saveDictType(dictTypeForm);
        return Result.judge(result);
    }

    @ApiOperation(value = "修改字典类型")
    @PutMapping("/{id}")
    public Result updateDict(@PathVariable Long id, @RequestBody DictTypeForm dictTypeForm) {
        boolean status = dictTypeService.updateDictType(id, dictTypeForm);
        return Result.judge(status);
    }

    @ApiOperation(value = "删除字典类型")
    @DeleteMapping("/{ids}")
    public Result deleteDictTypes(
            @ApiParam("字典类型ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = dictTypeService.deleteDictTypes(ids);
        return Result.judge(result);
    }
}
