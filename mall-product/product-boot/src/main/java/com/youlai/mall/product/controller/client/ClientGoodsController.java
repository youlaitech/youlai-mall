package com.youlai.mall.product.controller.client;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.mall.product.model.query.client.ClientGoodsPageQuery;
import com.youlai.mall.product.model.vo.client.ClientGoodsDetailVO;
import com.youlai.mall.product.model.vo.client.ClientGoodsPageVO;
import com.youlai.mall.product.service.client.ClientSpuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户端商品控制器
 *
 * @author Ray.Hao
 * @since 2024/5/17
 */
@Tag(name = "【客户端】商品接口")
@RestController
@RequestMapping("/api/client/v1/goods")
@RequiredArgsConstructor
public class ClientGoodsController {

    private final ClientSpuService clientSpuService;

    @Operation(summary = "商品分页列表")
    @GetMapping("/page")
    public PageResult<ClientGoodsPageVO> getGoodsPage(ClientGoodsPageQuery queryParams) {
        IPage<ClientGoodsPageVO> result = clientSpuService.getGoodsPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "获取商品详情")
    @GetMapping("/{spuId}")
    public Result<ClientGoodsDetailVO> getGoodsDetail(
            @Parameter(description = "SPU ID") @PathVariable Long spuId
    ) {
        ClientGoodsDetailVO clientGoodsDetailVO = clientSpuService.getGoodsDetail(spuId);
        return Result.success(clientGoodsDetailVO);
    }

}
