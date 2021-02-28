package com.youlai.mall.pms.controller.app;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.domain.PmsSku;
import com.youlai.mall.pms.pojo.dto.SkuDTO;
import com.youlai.mall.pms.pojo.vo.SkuInfoVO;
import com.youlai.mall.pms.pojo.vo.WareSkuStockVO;
import com.youlai.mall.pms.service.IPmsSkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "【移动端】商品库存")
@RestController("AppInventoryController")
@RequestMapping("/api.app/v1/inventories")
@AllArgsConstructor
public class InventoryController {

    private IPmsSkuService iPmsSkuService;


    @ApiOperation(value = "商品库存详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "商品SkuId", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result<SkuDTO> detail(@PathVariable Long id) {
        PmsSku sku = iPmsSkuService.getById(id);
        SkuDTO skuDTO = new SkuDTO();
        BeanUtil.copyProperties(sku, skuDTO);
        return Result.success(skuDTO);
    }


    @ApiImplicitParam(name = "id", value = "商品SkuId", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{skuId}/inventory")
    public Result<Integer> getInventoryBySkuId(@PathVariable Long skuId) {
        Integer inventory = iPmsSkuService.getInventoryBySkuId(skuId);
        return Result.success(inventory);
    }
}
