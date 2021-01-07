package com.youlai.mall.pms.controller.app;

import com.youlai.common.core.result.Result;
import com.youlai.mall.pms.pojo.PmsSku;
import com.youlai.mall.pms.service.IPmsSkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Api(tags = "商品库存")
@RestController
@RequestMapping("/api.app/v1/skus")
@Slf4j
@AllArgsConstructor
public class AppSkuController {

    private IPmsSkuService iPmsSkuService;

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
