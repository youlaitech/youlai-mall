package com.youlai.mall.product.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.mall.product.model.form.AttrForm;
import com.youlai.mall.product.model.form.AttrGroupForm;
import com.youlai.mall.product.model.query.AttributePageQuery;
import com.youlai.mall.product.model.vo.AttrPageVO;
import com.youlai.mall.product.service.AttrGroupService;
import com.youlai.mall.product.service.AttrService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    private final AttrGroupService attrGroupService;

    @ApiOperationSupport(order = 1)
    @Operation(summary = "属性组分页列表")
    @GetMapping("/group/page")
    public PageResult<AttrPageVO> listPagedAttributes(
            AttributePageQuery queryParams
    ) {
        IPage<AttrPageVO> page = attrService.listPagedAttributes(queryParams);
        return PageResult.success(page);
    }

    @ApiOperationSupport(order = 2)
    @Operation(summary = "新增属性组")
    @PostMapping("/group")
    public Result saveAttributeGroup(@RequestBody @Valid AttrGroupForm formData) {
        boolean result = attrGroupService.saveAttributeGroup(formData);
        return Result.judge(result);
    }

    @ApiOperationSupport(order = 3)
    @Operation(summary = "修改属性组")
    @PutMapping(value = "/group/{id}")
    public Result updateAttributeGroup(@Parameter(description = "属性组ID") @PathVariable Long id,
                                       @RequestBody @Validated AttrGroupForm formData) {
        boolean result = attrGroupService.updateAttributeGroup(id, formData);
        return Result.judge(result);
    }

    @ApiOperationSupport(order = 4)
    @Operation(summary = "删除属性组")
    @DeleteMapping("/group/{ids}")
    public Result deleteAttributeGroups(
            @Parameter(description = "属性组ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        attrGroupService.deleteAttributeGroups(ids);
        return Result.success();
    }

    @ApiOperationSupport(order = 5)
    @Operation(summary = "新增属性")
    @PostMapping
    public Result saveAttribute(@RequestBody @Valid AttrForm formData) {
        boolean result = attrService.saveAttribute(formData);
        return Result.judge(result);
    }

    @ApiOperationSupport(order = 6)
    @Operation(summary = "属性表单数据")
    @GetMapping("/{id}/form")
    public Result<AttrForm> getAttributeForm(
            @Parameter(description = "属性ID") @PathVariable Long id
    ) {
        AttrForm formData = attrService.getAttributeFormData(id);
        return Result.success(formData);
    }

    @ApiOperationSupport(order = 7)
    @Operation(summary = "修改属性")
    @PutMapping(value = "/{id}")
    public Result updateAttribute(@Parameter(description = "属性ID") @PathVariable Long id,
                                  @RequestBody @Validated AttrForm formData) {
        boolean result = attrService.updateAttribute(id, formData);
        return Result.judge(result);
    }

    @ApiOperationSupport(order = 8)
    @Operation(summary = "删除属性")
    @DeleteMapping("/{ids}")
    public Result deleteAttributes(
            @Parameter(description = "属性ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = attrService.deleteAttributes(ids);
        return Result.judge(result);
    }

}
