package com.youlai.mall.pms.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.form.PmsSpuForm;
import com.youlai.mall.pms.pojo.query.SpuPageQuery;
import com.youlai.mall.pms.pojo.vo.PmsSpuDetailVO;
import com.youlai.mall.pms.pojo.vo.PmsSpuPageVO;
import com.youlai.mall.pms.service.SpuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 「管理端」商品控制层
 *
 * @author haoxr
 * @date 2021/1/4
 **/
@Api(tags = "「管理端」商品SPU接口")
@RestController
@RequestMapping("/api/v1/spu")
@AllArgsConstructor
public class PmsSpuController {

    private SpuService spuServiced;

    @ApiOperation(value = "商品分页列表")
    @GetMapping("/pages")
    public PageResult listPmsSpuPages(SpuPageQuery queryParams) {
        IPage<PmsSpuPageVO> result = spuServiced.listPmsSpuPages(queryParams);
        return PageResult.success(result);
    }

    @ApiOperation(value = "商品详情")
    @GetMapping("/{id}")
    public Result detail( @ApiParam("商品ID") @PathVariable Long id) {
        PmsSpuDetailVO pmsSpuDetailVO = spuServiced.getPmsSpuDetail(id);
        return Result.success(pmsSpuDetailVO);
    }

    @ApiOperation(value = "新增商品")
    @PostMapping
    public Result addSpu(@RequestBody PmsSpuForm formData) {
        boolean result = spuServiced.addSpu(formData);
        return Result.judge(result);
    }

    @ApiOperation(value = "修改商品")
    @PutMapping(value = "/{id}")
    public Result updateSpuById(
            @ApiParam("商品ID") @PathVariable Long id,
            @RequestBody PmsSpuForm formData
    ) {
        boolean result = spuServiced.updateSpuById(id,formData);
        return Result.judge(result);
    }

    @ApiOperation(value = "删除商品")
    @DeleteMapping("/{ids}")
    public Result delete(@ApiParam("商品ID,多个以英文逗号(,)分隔") @PathVariable String ids) {
        boolean result = spuServiced.removeBySpuIds(ids);
        return Result.judge(result);
    }

}
