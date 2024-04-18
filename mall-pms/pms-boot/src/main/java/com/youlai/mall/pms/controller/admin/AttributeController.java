package com.youlai.mall.pms.controller.admin;

import com.youlai.mall.pms.model.form.AttributeGroupForm;
import com.youlai.mall.pms.model.query.AttributeGroupPageQuery;
import com.youlai.mall.pms.model.vo.AttributeGroupPageVO;
import com.youlai.mall.pms.service.AttributeGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.youlai.mall.pms.model.form.AttributeForm;
import com.youlai.mall.pms.model.query.AttributePageQuery;
import com.youlai.mall.pms.model.vo.AttributePageVO;
import com.youlai.mall.pms.service.AttributeService;
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
 * 属性 前端控制器
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
@Tag(name = "属性接口")
@RestController
@RequestMapping("/api/v1/attributes")
@RequiredArgsConstructor
public class AttributeController {

    private final AttributeService attributeService;

    @Operation(summary = "属性分页列表")
    @GetMapping("/page")
    public PageResult<AttributePageVO> listPagedAttributes(AttributePageQuery queryParams) {
        IPage<AttributePageVO> result = attributeService.listPagedAttributes(queryParams);
        return PageResult.success(result);
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


    private final AttributeGroupService attributeGroupService;

    @Operation(summary = "属性组分页列表")
    @GetMapping("/page")
    public PageResult<AttributeGroupPageVO> listPagedAttributeGroups(AttributeGroupPageQuery queryParams) {
        IPage<AttributeGroupPageVO> result = attributeGroupService.listPagedAttributeGroups(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增属性组")
    @PostMapping
    public Result saveAttributeGroup(@RequestBody @Valid AttributeGroupForm formData) {
        boolean result = attributeGroupService.saveAttributeGroup(formData);
        return Result.judge(result);
    }

    @Operation(summary = "属性组表单数据")
    @GetMapping("/{id}/form")
    public Result<AttributeGroupForm> getAttributeGroupForm(
            @Parameter(description = "属性组ID") @PathVariable Long id
    ) {
        AttributeGroupForm formData = attributeGroupService.getAttributeGroupFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改属性组")
    @PutMapping(value = "/{id}")
    public Result updateAttributeGroup(@Parameter(description = "属性组ID") @PathVariable Long id,
                                       @RequestBody @Validated AttributeGroupForm formData) {
        boolean result = attributeGroupService.updateAttributeGroup(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除属性组")
    @DeleteMapping("/{ids}")
    public Result deleteAttributeGroups(
            @Parameter(description = "属性组ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = attributeGroupService.deleteAttributeGroups(ids);
        return Result.judge(result);
    }
}
