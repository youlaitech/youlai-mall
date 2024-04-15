package com.youlai.mall.pms.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.mall.pms.model.form.PmsSpuForm;
import com.youlai.mall.pms.model.query.SpuPageQuery;
import com.youlai.mall.pms.model.vo.PmsSpuDetailVO;
import com.youlai.mall.pms.model.vo.PmsSpuPageVO;
import com.youlai.mall.pms.service.SpuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Admin-商品控制层
 *
 * @author haoxr
 * @since 2021/1/4
 **/
@Tag(name = "Admin-商品SPU接口")
@RestController
@RequestMapping("/api/v1/spu")
@AllArgsConstructor
public class SpuController {

    private SpuService spuService;

    @Operation(summary = "商品分页列表")
    @GetMapping("/page")
    public PageResult listPagedSpu(SpuPageQuery queryParams) {
        IPage<PmsSpuPageVO> result = spuService.listPagedSpu(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "商品详情")
    @GetMapping("/{id}/detail")
    public Result detail(@Parameter(name = "商品ID") @PathVariable Long id) {
        PmsSpuDetailVO pmsSpuDetailVO = spuService.getSpuDetail(id);
        return Result.success(pmsSpuDetailVO);
    }

    @Operation(summary = "新增商品")
    @PostMapping
    public Result addSpu(@RequestBody PmsSpuForm formData) {
        boolean result = spuService.addSpu(formData);
        return Result.judge(result);
    }

    @Operation(summary = "修改商品")
    @PutMapping(value = "/{id}")
    public Result updateSpuById(
            @Parameter(name = "商品ID") @PathVariable Long id,
            @RequestBody PmsSpuForm formData
    ) {
        boolean result = spuService.updateSpuById(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除商品")
    @DeleteMapping("/{ids}")
    public Result delete(
            @Parameter(name = "商品ID,多个以英文逗号(,)分隔") @PathVariable String ids
    ) {
        boolean result = spuService.removeBySpuIds(ids);
        return Result.judge(result);
    }

}
