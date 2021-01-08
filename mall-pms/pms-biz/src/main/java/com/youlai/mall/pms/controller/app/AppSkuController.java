package com.youlai.mall.pms.controller.app;

import cn.hutool.core.bean.BeanUtil;
import com.youlai.common.core.result.Result;
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

@Api(tags = "商品SKU")
@RestController
@RequestMapping("/api.app/v1/sku")
@Slf4j
@AllArgsConstructor
public class AppSkuController {

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


    @ApiOperation(value = "修改库存", httpMethod = "POST")
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
