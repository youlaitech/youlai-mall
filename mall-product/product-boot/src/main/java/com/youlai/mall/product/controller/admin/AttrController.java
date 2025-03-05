package com.youlai.mall.product.controller.admin;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.youlai.common.result.Result;
import com.youlai.mall.product.model.form.AttrForm;
import com.youlai.mall.product.service.AttrService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 商品属性控制器
 *
 * @author Ray.Hao
 * @since 2024/4/19
 */
@Tag(name = "【Admin】商品属性接口")
@RestController
@RequestMapping("/api/v1/attrs")
@RequiredArgsConstructor
public class AttrController {

    private final AttrService attrService;

    @ApiOperationSupport(order = 2)
    @Operation(summary = "保存属性")
    @PostMapping
    public Result saveAttr(@RequestBody @Valid AttrForm formData) {
        boolean result = attrService.saveAttr(formData);
        return Result.judge(result);
    }

    @ApiOperationSupport(order = 3)
    @Operation(summary = "获取属性表单")
    @GetMapping("/{id}/form")
    public Result<AttrForm> getAttrForm(
            @Parameter(description = "属性组ID") @PathVariable Long id
    ) {
        AttrForm formData = attrService.getAttrForm(id);
        return Result.success(formData);
    }

    @ApiOperationSupport(order = 4)
    @Operation(summary = "修改属性")
    @PutMapping(value = "/{id}")
    public Result updateAttr(
            @Parameter(description = "属性组ID") @PathVariable Long id,
            @RequestBody @Validated AttrForm formData
    ) {
        boolean result = attrService.updateAttr(id, formData);
        return Result.judge(result);
    }

    @ApiOperationSupport(order = 5)
    @Operation(summary = "删除属性")
    @DeleteMapping("/{ids}")
    public Result deleteAttrs(
            @Parameter(description = "属性组ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        attrService.deleteAttrs(ids);
        return Result.success();
    }


}
