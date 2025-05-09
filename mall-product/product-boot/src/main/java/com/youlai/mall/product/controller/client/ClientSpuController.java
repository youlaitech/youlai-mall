package com.youlai.mall.product.controller.client;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.mall.product.model.query.ProductPageQuery;
import com.youlai.mall.product.model.vo.client.ClientSpuDetailVO;
import com.youlai.mall.product.model.vo.client.ClientSpuPageVO;
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
 * 客户端 SPU 控制器
 *
 * @author Ray.Hao
 * @since 2024/5/17
 */
@Tag(name = "【客户端】SPU 接口")
@RestController
@RequestMapping("/api/client/v1/products")
@RequiredArgsConstructor
public class ClientSpuController {

    private final ClientSpuService clientSpuService;

    @Operation(summary = "商品分页列表")
    @GetMapping("/page")
    public PageResult<ClientSpuPageVO> getSpuPage(ProductPageQuery queryParams) {
        IPage<ClientSpuPageVO> result = clientSpuService.getSpuPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "获取商品详情")
    @GetMapping("/{id}")
    public Result<ClientSpuDetailVO> getSpuDetail(
            @Parameter(description = "商品ID") @PathVariable Long id
    ) {
        ClientSpuDetailVO clientSpuDetailVO = clientSpuService.getSpuDetail(id);
        return Result.success(clientSpuDetailVO);
    }

}
