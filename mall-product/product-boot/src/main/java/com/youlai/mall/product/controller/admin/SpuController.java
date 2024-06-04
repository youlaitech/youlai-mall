package com.youlai.mall.product.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.mall.product.model.form.SpuForm;
import com.youlai.mall.product.model.query.SpuPageQuery;
import com.youlai.mall.product.model.vo.SpuPageVO;
import com.youlai.mall.product.service.SpuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Admin-商品控制层
 *
 * @author haoxr
 * @since 2021/1/4
 **/
@Tag(name = "【Admin】商品接口")
@RestController
@RequestMapping("/api/v1/spu")
@RequiredArgsConstructor
public class SpuController {

    private final SpuService spuService;

    @Operation(summary = "商品分页列表")
    @GetMapping("/page")
    public PageResult<SpuPageVO> listPagedSpu(SpuPageQuery queryParams) {
        IPage<SpuPageVO> result = spuService.listPagedSpu(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "获取商品表单数据")
    @GetMapping("/{spuId}/form")
    public Result<SpuForm> getSpuForm(
            @Parameter(description = "SPU ID",example = "290") @PathVariable Long spuId
    ) {
        SpuForm spuForm = spuService.getSpuForm(spuId);
        return Result.success(spuForm);
    }

    @Operation(summary = "保存商品")
    @PostMapping
    public Result saveSpu(@Validated @RequestBody SpuForm formData) {
        boolean result = spuService.saveSpu(formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除商品")
    @DeleteMapping("/{ids}")
    public Result deleteSpu(
            @Parameter(description = "SPU ID,多个以英文逗号(,)分隔") @PathVariable String ids
    ) {
        boolean result = spuService.removeBySpuIds(ids);
        return Result.judge(result);
    }

}
