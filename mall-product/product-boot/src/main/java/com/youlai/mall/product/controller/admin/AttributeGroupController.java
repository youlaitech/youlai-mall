package com.youlai.mall.product.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.common.web.model.Option;
import com.youlai.mall.product.model.form.AttributeGroupForm;
import com.youlai.mall.product.model.query.AttributeGroupPageQuery;
import com.youlai.mall.product.model.vo.AttributeGroupPageVO;
import com.youlai.mall.product.service.AttributeGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 属性组前端控制器
 *
 * @author Ray Hao
 * @since 2024-04-19
 */
@Tag(name = "属性组接口")
@RestController
@RequestMapping("/api/v1/attribute-groups")
@RequiredArgsConstructor
public class AttributeGroupController {

    private final AttributeGroupService attributeGroupService;

    @Operation(summary = "属性组分页列表")
    @GetMapping("/page")
    public PageResult<AttributeGroupPageVO> listPagedAttributeGroups(AttributeGroupPageQuery queryParams) {
        IPage<AttributeGroupPageVO> result = attributeGroupService.listPagedAttributeGroups(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "属性分页列表")
    @GetMapping("/options")
    public Result<List<Option>> listAttributeOptions(@Parameter(description = "属性组ID") Long categoryId) {
        List<Option> list = attributeGroupService.listAttributeOptions(categoryId);
        return Result.success(list);
    }

    @Operation(summary = "新增属性组")
    @PostMapping
    public Result saveAttributeGroup(@RequestBody @Valid AttributeGroupForm formData) {
        boolean result = attributeGroupService.saveAttributeGroup(formData);
        return Result.judge(result);
    }

    @Operation(summary = "属性组表单数据")
    @GetMapping("//{id}/form")
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
        attributeGroupService.deleteAttributeGroups(ids);
        return Result.success();
    }


}
