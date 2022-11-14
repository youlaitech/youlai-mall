package com.youlai.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.common.web.model.Option;
import com.youlai.system.pojo.form.DictTypeForm;
import com.youlai.system.pojo.query.DictTypePageQuery;
import com.youlai.system.service.SysDictTypeService;
import com.youlai.system.pojo.vo.dict.DictTypePageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "字典类型接口")
@RestController
@RequestMapping("/api/v1/dict/types")
@RequiredArgsConstructor
public class SysDictTypeController {

    private final SysDictTypeService dictTypeService;

    @ApiOperation(value = "字典类型分页列表")
    @GetMapping("/pages")
    public PageResult<DictTypePageVO> listDictTypePages(DictTypePageQuery queryParams) {
        Page<DictTypePageVO> result = dictTypeService.listDictTypePages(queryParams);
        return PageResult.success(result);
    }

    @ApiOperation(value = "字典类型表单详情")
    @GetMapping("/{id}/form")
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

    @ApiOperation(value = "获取字典类型的数据项")
    @GetMapping("/{typeCode}/items")
    public Result<List<Option>> listDictItemsByTypeCode(
            @ApiParam("字典类型编码") @PathVariable String typeCode
    ) {
        List<Option> list = dictTypeService.listDictItemsByTypeCode(typeCode);
        return Result.success(list);
    }
}
