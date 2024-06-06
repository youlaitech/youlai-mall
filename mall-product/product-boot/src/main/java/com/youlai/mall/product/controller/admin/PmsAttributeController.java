package com.youlai.mall.product.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.youlai.mall.product.model.form.AttributeForm;
import com.youlai.mall.product.model.query.AttributePageQuery;
import com.youlai.mall.product.model.vo.AttributePageVO;
import com.youlai.mall.product.service.AttributeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

/**
 * 属性前端控制器
 *
 * @author Ray Hao
 * @since 2024/4/19
 */
@Tag(name = "【Admin】属性接口")
@RestController
@RequestMapping("/api/v1/attributes")
@RequiredArgsConstructor
public class PmsAttributeController {

    private final AttributeService attributeService;

    @Operation(summary = "属性分页列表")
    @GetMapping("/page")
    public PageResult<AttributePageVO> listPagedAttributes(
            AttributePageQuery queryParams
    ) {
        IPage<AttributePageVO> page = attributeService.listPagedAttributes(queryParams);
        return PageResult.success(page);
    }

    @Operation(summary = "新增属性")
    @PostMapping
    public Result saveAttribute(@RequestBody @Valid AttributeForm formData) {
        boolean result = attributeService.saveAttribute(formData);
        return Result.judge(result);
    }

    @Operation(summary = "属性表单数据")
    @GetMapping("/{id}/form")
    public Result<AttributeForm> getAttributeForm(
            @Parameter(description = "属性ID") @PathVariable Long id
    ) {
        AttributeForm formData = attributeService.getAttributeFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改属性")
    @PutMapping(value = "/{id}")
    public Result updateAttribute(@Parameter(description = "属性ID") @PathVariable Long id,
                                  @RequestBody @Validated AttributeForm formData) {
        boolean result = attributeService.updateAttribute(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除属性")
    @DeleteMapping("/{ids}")
    public Result deleteAttributes(
            @Parameter(description = "属性ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = attributeService.deleteAttributes(ids);
        return Result.judge(result);
    }


}
