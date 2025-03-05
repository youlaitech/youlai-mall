package com.youlai.mall.product.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.youlai.mall.product.model.form.SpecForm;
import com.youlai.mall.product.service.SpecService;
import com.youlai.common.result.Result;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

/**
 * 商品规格控制器
 *
 * @author Ray.Hao
 * @since 2024-06-13
 */
@Tag(name = "【Admin】规格接口")
@RestController
@RequestMapping("/api/v1/specs")
@RequiredArgsConstructor
public class SpecController {

    private final SpecService specService;

    @Operation(summary = "新增规格")
    @PostMapping
    public Result saveSpec(
            @RequestBody @Valid SpecForm formData
    ) {
        boolean result = specService.saveSpec(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取规格表单数据")
    @GetMapping("/{id}/form")
    public Result<SpecForm> getSpecForm(
            @Parameter(description = "规格ID") @PathVariable Long id
    ) {
        SpecForm formData = specService.getSpecFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改规格")
    @PutMapping(value = "/{id}")
    public Result updateSpec(
            @Parameter(description = "规格ID") @PathVariable Long id,
            @RequestBody @Validated SpecForm formData
    ) {
        boolean result = specService.updateSpec(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除规格")
    @DeleteMapping("/{ids}")
    public Result deleteSpecs(
            @Parameter(description = "规格ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = specService.deleteSpecs(ids);
        return Result.judge(result);
    }
}
