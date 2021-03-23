package com.youlai.mall.pms.controller.app;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.bo.app.ProductBO;
import com.youlai.mall.pms.pojo.domain.PmsSpu;
import com.youlai.mall.pms.pojo.dto.app.ProductDTO;
import com.youlai.mall.pms.service.IPmsSpuService;
import com.youlai.mall.pms.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "【移动端】商品信息")
@RestController("AppSpuController")
@RequestMapping("/api.app/v1/products")
@AllArgsConstructor
public class ProductController {

    private IPmsSpuService iPmsSpuService;

    private IProductService iProductService;

    @ApiOperation(value = "列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "limit", value = "每页数量", defaultValue = "10", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "name", value = "商品名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "categoryId", value = "商品类目", paramType = "query", dataType = "Long")
    })
    @GetMapping
    public Result list(
            Integer page,
            Integer limit,
            String name,
            Long categoryId
    ) {
        Page<PmsSpu> result = iPmsSpuService.page(new Page<>(page, limit), new LambdaQueryWrapper<PmsSpu>()
                .eq(categoryId != null, PmsSpu::getCategoryId, categoryId)
                .like(StrUtil.isNotBlank(name), PmsSpu::getName, name)
                .select(PmsSpu::getId,
                        PmsSpu::getName,
                        PmsSpu::getPic,
                        PmsSpu::getPrice,
                        PmsSpu::getSales
                )
        );
        List<ProductDTO> list = result.getRecords().stream()
                .map(item -> JSONUtil.toBean(JSONUtil.toJsonStr(item), ProductDTO.class))
                .collect(Collectors.toList());
        return Result.success(list, result.getTotal());
    }

    @ApiOperation(value = "商品详情")
    @ApiImplicitParam(name = "id", value = "商品ID", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        ProductBO product = iProductService.getProductById(id);
        return Result.success(product);
    }

}
