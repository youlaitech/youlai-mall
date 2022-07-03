package com.youlai.mall.pms.controller.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.query.SpuPageQuery;
import com.youlai.mall.pms.pojo.vo.SpuPageVO;
import com.youlai.mall.pms.pojo.vo.SpuDetailVO;
import com.youlai.mall.pms.service.IPmsSpuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "「移动端」商品信息")
@RestController("appSpuController")
@RequestMapping("/app-api/v1/spu")
@RequiredArgsConstructor
public class SpuController {

    private final IPmsSpuService iPmsSpuService;

    @ApiOperation(value = "商品分页列表")
    @GetMapping("/page")
    public PageResult listSpuPage(SpuPageQuery queryParams) {
        IPage<SpuPageVO> result = iPmsSpuService.listSpuPages(queryParams);
        return PageResult.success(result);
    }

    @ApiOperation(value = "获取商品详情")
    @GetMapping("/{spuId}")
    public Result<SpuDetailVO> getSpuDetail(
            @ApiParam("商品ID") @PathVariable Long spuId
    ) {
        SpuDetailVO spuDetailVO = iPmsSpuService.getSpuDetail(spuId);
        return Result.success(spuDetailVO);
    }

}
