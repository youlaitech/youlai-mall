package com.youlai.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.core.annotation.Log;
import com.youlai.common.core.annotation.RepeatSubmit;
import com.youlai.common.core.model.Option;
import com.youlai.common.enums.LogModuleEnum;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.system.model.form.DictDataForm;
import com.youlai.system.model.query.DictDataPageQuery;
import com.youlai.system.model.vo.DictDataPageVO;
import com.youlai.system.service.DictDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典数据控制层
 *
 * @author Ray
 * @since 2.9.0
 */
@Tag(name = "07.字典数据接口")
@RestController
@RequestMapping("/api/v1/dict-data")
@RequiredArgsConstructor
public class DictDataController {

    private final DictDataService dictDataService;

    @Operation(summary = "字典数据分页列表")
    @GetMapping("/page")
    @Log( value = "字典数据分页列表",module = LogModuleEnum.DICT)
    public PageResult<DictDataPageVO> getDictDataPage(
            DictDataPageQuery queryParams
    ) {
        Page<DictDataPageVO> result = dictDataService.getDictDataPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "字典数据表单内")
    @GetMapping("/{id}/form")
    public Result<DictDataForm> getDictDataForm(
            @Parameter(description = "字典数据ID") @PathVariable Long id
    ) {
        DictDataForm formData = dictDataService.getDictDataForm(id);
        return Result.success(formData);
    }

    @Operation(summary = "新增字典数据")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('sys:dict-data:add')")
    @RepeatSubmit
    public Result<Void> saveDictData(@Valid @RequestBody DictDataForm formData) {
        boolean result = dictDataService.saveDictData(formData);
        return Result.judge(result);
    }

    @Operation(summary = "修改字典数据")
    @PutMapping("/{id}")
    @PreAuthorize("@ss.hasPerm('sys:dict-data:edit')")
    public Result<?> updateDictData(
            @PathVariable Long id,
            @RequestBody DictDataForm formData
    ) {
        boolean status = dictDataService.updateDictData(formData);
        return Result.judge(status);
    }

    @Operation(summary = "删除字典数据")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:dict-data:delete')")
    public Result<Void> deleteDictionaries(
            @Parameter(description = "字典ID，多个以英文逗号(,)拼接") @PathVariable String ids
    ) {
        dictDataService.deleteDictDataByIds(ids);
        return Result.success();
    }

    @Operation(summary = "字典数据列表")
    @GetMapping("/{dictCode}/options")
    public Result<List<Option<String>>> getDictDataList(
            @Parameter(description = "字典编码") @PathVariable String dictCode
    ) {
        List<Option<String>> options = dictDataService.getDictDataList(dictCode);
        return Result.success(options);
    }

}
