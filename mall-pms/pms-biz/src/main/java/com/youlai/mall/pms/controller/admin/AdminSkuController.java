package com.youlai.mall.pms.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import com.youlai.common.core.result.Result;
import com.youlai.mall.pms.bo.ProductBO;
import com.youlai.mall.pms.pojo.PmsSku;
import com.youlai.mall.pms.pojo.dto.SkuDTO;
import com.youlai.mall.pms.service.IPmsSkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Api(tags = "商品sku接口")
@RestController
@RequestMapping("/api.admin/v1/sku")
@Slf4j
@AllArgsConstructor
public class AdminSkuController {

    private IPmsSkuService iPmsSkuService;

    @ApiOperation(value = "商品sku详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "商品sku id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result<SkuDTO> detail(@PathVariable Long id) {
        PmsSku sku = iPmsSkuService.getById(id);
        SkuDTO skuDTO = new SkuDTO();
        BeanUtil.copyProperties(sku, skuDTO);
        return Result.success(skuDTO);
    }

    @ApiOperation(value = "修改商品库存", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "sku", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsSku")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Long id,
            @RequestBody PmsSku sku) {
        boolean status = iPmsSkuService.updateById(sku);
        return Result.status(status);
    }


    @ApiOperation(value = "修改库存", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Sku ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "num", value = "库存数量", required = true, paramType = "query", dataType = "Integer")
    })
    @PutMapping("/{id}/stock")
    public Result updateStock(@PathVariable Long id, @RequestParam Integer num) {
        PmsSku sku = iPmsSkuService.getById(id);
        sku.setStock(sku.getStock() + num);
        boolean result = iPmsSkuService.updateById(sku);
        return Result.status(result);
    }

}
