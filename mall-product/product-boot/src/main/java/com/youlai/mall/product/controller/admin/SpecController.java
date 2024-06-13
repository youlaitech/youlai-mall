package com.youlai.mall.product.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.youlai.mall.product.model.form.SpecForm;
import com.youlai.mall.product.model.query.SpecPageQuery;
import com.youlai.mall.product.model.vo.SpecPageVO;
import com.youlai.mall.product.service.SpecService;
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
 *  规格前端控制器
 *
 * @author Ray Hao
 * @since 2024-06-13
 */
@Tag(name = "商品规格接口")
@RestController
@RequestMapping("/api/v1/specs")
@RequiredArgsConstructor
public class SpecController {

        private final SpecService specService;

        @Operation(summary = "分页列表")
        @GetMapping("/page")
        public PageResult<SpecPageVO> listPagedSpecs(SpecPageQuery queryParams ) {
            IPage<SpecPageVO> result = specService.listPagedSpecs(queryParams);
            return PageResult.success(result);
        }

        @Operation(summary = "新增")
        @PostMapping
        public Result saveSpec(@RequestBody @Valid SpecForm formData ) {
            boolean result = specService.saveSpec(formData);
            return Result.judge(result);
        }

        @Operation(summary = "表单数据")
        @GetMapping("/{id}/form")
        public Result<SpecForm> getSpecForm(
            @Parameter(description = "ID") @PathVariable Long id
        ) {
            SpecForm formData = specService.getSpecFormData(id);
            return Result.success(formData);
        }

        @Operation(summary = "修改")
        @PutMapping(value = "/{id}")
        public Result updateSpec(@Parameter(description = "ID") @PathVariable Long id,
        @RequestBody @Validated SpecForm formData) {
            boolean result = specService.updateSpec(id, formData);
            return Result.judge(result);
        }

        @Operation(summary = "删除")
        @DeleteMapping("/{ids}")
        public Result deleteSpecs(
            @Parameter(description = "ID，多个以英文逗号(,)分割") @PathVariable String ids
        ) {
            boolean result = specService.deleteSpecs(ids);
            return Result.judge(result);
        }
}
