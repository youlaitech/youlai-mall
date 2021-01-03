package com.youlai.mall.pms.controller.admin;

import com.youlai.common.core.result.Result;
import com.youlai.mall.pms.pojo.PmsSku;
import com.youlai.mall.pms.service.IPmsSkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "商品sku接口")
@RestController
@RequestMapping("/admin-api/v1/sku")
@Slf4j
@AllArgsConstructor
public class AdminSkuController {

    private IPmsSkuService skuService;

    @ApiOperation(value = "商品sku详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "商品sku id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result<PmsSku> detail(@PathVariable Long id) {
        PmsSku pmsSku = skuService.getById(id);
        return Result.success(pmsSku);

    }
}
