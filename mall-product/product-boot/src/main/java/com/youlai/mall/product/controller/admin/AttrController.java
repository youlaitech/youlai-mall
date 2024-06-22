package com.youlai.mall.product.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.mall.product.model.form.AttrGroupForm;
import com.youlai.mall.product.model.query.AttrGroupPageQuery;
import com.youlai.mall.product.model.vo.AttrGroupPageVO;
import com.youlai.mall.product.service.AttrGroupService;
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
public class AttrController {

    private final AttrGroupService attrGroupService;

    @ApiOperationSupport(order = 1)
    @Operation(summary = "属性分页列表")
    @GetMapping("/page")
    public PageResult<AttrGroupPageVO> listPagedAttrGroups(
            AttrGroupPageQuery queryParams
    ) {
        IPage<AttrGroupPageVO> page = attrGroupService.listPagedAttrGroups(queryParams);
        return PageResult.success(page);
    }

    @ApiOperationSupport(order = 2)
    @Operation(summary = "保存属性")
    @PostMapping
    public Result saveAttrGroup(@RequestBody @Valid AttrGroupForm formData) {
        boolean result = attrGroupService.saveAttrGroup(formData);
        return Result.judge(result);
    }

    @ApiOperationSupport(order = 3)
    @Operation(summary = "获取属性表单")
    @GetMapping("/{id}/form")
    public Result<AttrGroupForm> getAttrGroupForm(
            @Parameter(description = "属性组ID") @PathVariable Long id
    ) {
        AttrGroupForm formData = attrGroupService.getAttrGroupForm(id);
        return Result.success(formData);
    }

    @ApiOperationSupport(order = 4)
    @Operation(summary = "修改属性")
    @PutMapping(value = "/{id}")
    public Result updateAttrGroup(
            @Parameter(description = "属性组ID") @PathVariable Long id,
            @RequestBody @Validated AttrGroupForm formData
    ) {
        boolean result = attrGroupService.updateAttrGroup(id, formData);
        return Result.judge(result);
    }

    @ApiOperationSupport(order = 5)
    @Operation(summary = "删除属性")
    @DeleteMapping("/{ids}")
    public Result deleteAttrGroups(
            @Parameter(description = "属性组ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        attrGroupService.deleteAttrGroups(ids);
        return Result.success();
    }


}
