package com.youlai.mall.pms.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.domain.PmsSku;
import com.youlai.mall.pms.pojo.dto.SkuDTO;
import com.youlai.mall.pms.service.IPmsSkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Api(tags = "【系统管理】商品库存")
@RestController
@RequestMapping("/api.admin/v1/skus")
@Slf4j
@AllArgsConstructor
public class SkuController {

    private IPmsSkuService iPmsSkuService;

    @ApiOperation(value = "商品详情")
    @ApiImplicitParam(name = "id", value = "商品SkuID", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        PmsSku sku = iPmsSkuService.getById(id);
        return Result.success(sku);
    }

    @ApiOperation(value = "修改库存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "sku", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsSku")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Long id,
            @RequestBody PmsSku sku) {
        boolean status = iPmsSkuService.updateById(sku);
        return Result.judge(status);
    }


    @ApiOperation(value = "修改库存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Sku ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "num", value = "库存数量", required = true, paramType = "query", dataType = "Long")
    })
    @PutMapping("/{id}/stock")
    public Result updateStock(@PathVariable Long id, @RequestParam Integer num) {
        PmsSku sku = iPmsSkuService.getById(id);
        sku.setStock(sku.getStock() + num);
        boolean result = iPmsSkuService.updateById(sku);
        return Result.judge(result);
    }

}
