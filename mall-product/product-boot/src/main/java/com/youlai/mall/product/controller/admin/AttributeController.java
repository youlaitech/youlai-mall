package com.youlai.mall.product.controller.admin;

import com.youlai.mall.product.model.form.AttrForm;
import com.youlai.mall.product.model.vo.AttributeGroupVO;
import com.youlai.mall.product.model.vo.AttributeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.youlai.mall.product.model.query.AttributePageQuery;
import com.youlai.mall.product.model.vo.AttributePageVO;
import com.youlai.mall.product.service.AttrService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

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
public class AttributeController {

    private final AttrService attrService;

    @Operation(summary = "属性分页列表")
    @GetMapping("/page")
    public PageResult<AttributePageVO> listPagedAttributes(
            AttributePageQuery queryParams
    ) {
        IPage<AttributePageVO> page = attrService.listPagedAttributes(queryParams);
        return PageResult.success(page);
    }

    @Operation(summary = "新增属性")
    @PostMapping
    public Result saveAttribute(@RequestBody @Valid AttrForm formData) {
        boolean result = attrService.saveAttribute(formData);
        return Result.judge(result);
    }

    @Operation(summary = "属性表单数据")
    @GetMapping("/{id}/form")
    public Result<AttrForm> getAttributeForm(
            @Parameter(description = "属性ID") @PathVariable Long id
    ) {
        AttrForm formData = attrService.getAttributeFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改属性")
    @PutMapping(value = "/{id}")
    public Result updateAttribute(@Parameter(description = "属性ID") @PathVariable Long id,
                                  @RequestBody @Validated AttrForm formData) {
        boolean result = attrService.updateAttribute(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除属性")
    @DeleteMapping("/{ids}")
    public Result deleteAttributes(
            @Parameter(description = "属性ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = attrService.deleteAttributes(ids);
        return Result.judge(result);
    }

    @Operation(summary = "获取基础属性列表")
    @GetMapping("/base")
    public Result<List<AttributeGroupVO>> listBaseAttributes(
            @Parameter(description = "商品分类ID", example = "3") @RequestParam Long categoryId
    ) {
        List<AttributeGroupVO> list = attrService.listBaseAttributes(categoryId);
        return Result.success(list);
    }

    @Operation(summary = "获取销售属性列表")
    @GetMapping("/sale")
    public Result<List<AttributeVO>> listSaleAttributes(
            @Parameter(description = "商品分类ID", example = "3") @RequestParam Long categoryId
    ) {
        List<AttributeVO> list = attrService.listSaleAttributes(categoryId);
        return Result.success(list);
    }

}