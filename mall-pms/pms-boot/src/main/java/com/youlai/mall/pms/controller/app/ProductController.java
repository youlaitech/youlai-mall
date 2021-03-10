package com.youlai.mall.pms.controller.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.bo.app.ProductBO;
import com.youlai.mall.pms.pojo.domain.PmsSpu;
import com.youlai.mall.pms.service.IPmsSpuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "【移动端】商品信息")
@RestController("AppProductController")
@RequestMapping("/api.app/v1/products")
@AllArgsConstructor
public class ProductController {

    private IPmsSpuService iPmsSpuService;

    @ApiOperation(value = "列表分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "name", value = "商品名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "categoryId", value = "商品类目", paramType = "query", dataType = "Long")
    })
    @GetMapping
    public Result list(
            Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer limit,
            String name,
            Long categoryId
    ) {
        IPage<PmsSpu> result = iPmsSpuService.list(
                new Page<>(page, limit),
                new PmsSpu().setName(name).setCategoryId(categoryId)
        );
        return Result.success(result.getRecords(), result.getTotal());
    }

    @ApiOperation(value = "商品详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "商品id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result<ProductBO> detail(@PathVariable Long id) {
        ProductBO product = iPmsSpuService.getProductByIdForApp(id);
        return Result.success(product);
    }

}
