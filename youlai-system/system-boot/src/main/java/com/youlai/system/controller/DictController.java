package com.youlai.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.core.annotation.Log;
import com.youlai.common.core.annotation.RepeatSubmit;
import com.youlai.common.enums.LogModuleEnum;
import com.youlai.common.core.model.Option;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.system.model.form.DictForm;
import com.youlai.system.model.form.DictItemForm;
import com.youlai.system.model.query.DictItemPageQuery;
import com.youlai.system.model.query.DictPageQuery;
import com.youlai.system.model.vo.DictItemOptionVO;
import com.youlai.system.model.vo.DictItemPageVO;
import com.youlai.system.model.vo.DictPageVO;
import com.youlai.system.service.DictItemService;
import com.youlai.system.service.DictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 字典控制层
 *
 * @author Ray.Hao
 * @since 2.9.0
 */
@Tag(name = "06.字典接口")
@RestController
@SuppressWarnings("SpellCheckingInspection")
@RequestMapping("/api/v1/dicts")
@RequiredArgsConstructor
public class DictController {

    private final DictService dictService;
    private final DictItemService dictItemService;


    //---------------------------------------------------
    // 字典相关接口
    //---------------------------------------------------
    @Operation(summary = "字典分页列表")
    @GetMapping("/page")
    @Log( value = "字典分页列表",module = LogModuleEnum.DICT)
    public PageResult<DictPageVO> getDictPage(
            DictPageQuery queryParams
    ) {
        Page<DictPageVO> result = dictService.getDictPage(queryParams);
        return PageResult.success(result);
    }


    @Operation(summary = "字典列表")
    @GetMapping
    public Result<List<Option<String>>> getDictList() {
        List<Option<String>> list = dictService.getDictList();
        return Result.success(list);
    }

    @Operation(summary = "字典表单数据")
    @GetMapping("/{id}/form")
    public Result<DictForm> getDictForm(
            @Parameter(description = "字典ID") @PathVariable Long id
    ) {
        DictForm formData = dictService.getDictForm(id);
        return Result.success(formData);
    }

    @Operation(summary = "新增字典")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('sys:dict:add')")
    @RepeatSubmit
    public Result<?> saveDict(@Valid @RequestBody DictForm formData) {
        boolean result = dictService.saveDict(formData);
        return Result.judge(result);
    }

    @Operation(summary = "修改字典")
    @PutMapping("/{id}")
    @PreAuthorize("@ss.hasPerm('sys:dict:edit')")
    public Result<?> updateDict(
            @PathVariable Long id,
            @RequestBody DictForm DictForm
    ) {
        boolean status = dictService.updateDict(id, DictForm);
        return Result.judge(status);
    }

    @Operation(summary = "删除字典")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:dict:delete')")
    public Result<?> deleteDictionaries(
            @Parameter(description = "字典ID，多个以英文逗号(,)拼接") @PathVariable String ids
    ) {
        dictService.deleteDictByIds(Arrays.stream(ids.split(",")).toList());
        return Result.success();
    }


    //---------------------------------------------------
    // 字典项相关接口
    //---------------------------------------------------
    @Operation(summary = "字典项分页")
    @GetMapping("/{dictCode}/items/page")
    public PageResult<DictItemPageVO> getDictItemPage(
            @PathVariable String dictCode,
            DictItemPageQuery queryParams
    ) {
        queryParams.setDictCode(dictCode);
        Page<DictItemPageVO> result = dictItemService.getDictItemPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "字典项列表")
    @GetMapping("/{dictCode}/items")
    public Result<List<DictItemOptionVO>> getDictItems(
            @Parameter(description = "字典编码") @PathVariable String dictCode
    ) {
        List<DictItemOptionVO> list = dictItemService.getDictItems(dictCode);
        return Result.success(list);
    }

    @Operation(summary = "新增字典项")
    @PostMapping("/{dictCode}/items")
    @PreAuthorize("@ss.hasPerm('sys:dict-item:add')")
    @RepeatSubmit
    public Result<Void> saveDictItem(
            @PathVariable String dictCode,
            @Valid @RequestBody DictItemForm formData
    ) {
        formData.setDictCode(dictCode);
        boolean result = dictItemService.saveDictItem(formData);
        return Result.judge(result);
    }

    @Operation(summary = "字典项表单数据")
    @GetMapping("/{dictCode}/items/{itemId}/form")
    public Result<DictItemForm> getDictItemForm(
            @PathVariable String dictCode,
            @Parameter(description = "字典项ID") @PathVariable Long itemId
    ) {
        DictItemForm formData = dictItemService.getDictItemForm(itemId);
        return Result.success(formData);
    }

    @Operation(summary = "修改字典项")
    @PutMapping("/{dictCode}/items/{itemId}")
    @PreAuthorize("@ss.hasPerm('sys:dict-item:edit')")
    @RepeatSubmit
    public Result<?> updateDictItem(
            @PathVariable String dictCode,
            @PathVariable Long itemId,
            @RequestBody DictItemForm formData
    ) {
        formData.setId(itemId);
        formData.setDictCode(dictCode);
        boolean status = dictItemService.updateDictItem(formData);
        return Result.judge(status);
    }

    @Operation(summary = "删除字典项")
    @DeleteMapping("/{dictCode}/items/{itemIds}")
    @PreAuthorize("@ss.hasPerm('sys:dict-item:delete')")
    public Result<Void> deleteDictItems(
            @Parameter(description = "字典ID，多个以英文逗号(,)拼接") @PathVariable String itemIds
    ) {
        dictItemService.deleteDictItemByIds(itemIds);
        return Result.success();
    }

}
