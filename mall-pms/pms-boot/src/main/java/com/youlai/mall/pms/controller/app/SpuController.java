package com.youlai.mall.pms.controller.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.query.SpuPageQuery;
import com.youlai.mall.pms.pojo.vo.GoodsPageVO;
import com.youlai.mall.pms.pojo.vo.GoodsDetailVO;
import com.youlai.mall.pms.service.IPmsSpuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "「移动端」商品信息")
@RestController("appSpuController")
@RequestMapping("/app-api/v1/spu")
@RequiredArgsConstructor
public class SpuController {

    private final IPmsSpuService iPmsSpuService;

    @ApiOperation(value = "商品分页列表")
    @GetMapping("/page")
    public PageResult listSpuPage(SpuPageQuery queryParams) {
        IPage<GoodsPageVO> result = iPmsSpuService.listAppSpuPage(queryParams);
        return PageResult.success(result);
    }

    @ApiOperation(value = "获取商品详情")
    @GetMapping("/{spuId}")
    public Result<GoodsDetailVO> getSpuDetail(
            @ApiParam("商品ID") @PathVariable Long spuId
    ) {
        GoodsDetailVO goodsDetailVO = iPmsSpuService.getAppSpuDetail(spuId);
        return Result.success(goodsDetailVO);
    }

}
