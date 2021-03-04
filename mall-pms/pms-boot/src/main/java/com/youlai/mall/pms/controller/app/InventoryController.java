package com.youlai.mall.pms.controller.app;

import cn.hutool.core.bean.BeanUtil;
import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.domain.PmsInventory;
import com.youlai.mall.pms.pojo.dto.SkuDTO;
import com.youlai.mall.pms.pojo.vo.WareSkuStockVO;
import com.youlai.mall.pms.service.IPmsInventoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "【移动端】商品库存")
@RestController("AppInventoryController")
@RequestMapping("/api.app/v1/inventories")
@AllArgsConstructor
public class InventoryController {

    private IPmsInventoryService iPmsInventoryService;


    @ApiOperation(value = "商品库存详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "商品库存ID", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result<SkuDTO> detail(@PathVariable Long id) {
        PmsInventory sku = iPmsInventoryService.getById(id);
        SkuDTO skuDTO = new SkuDTO();
        BeanUtil.copyProperties(sku, skuDTO);
        return Result.success(skuDTO);
    }


    @ApiImplicitParam(name = "id", value = "商品库存ID", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}/inventory")
    public Result<Integer> getInventoryById(@PathVariable Long id) {
        Integer inventory = iPmsInventoryService.getInventoryById(id);
        return Result.success(inventory);
    }


    @ApiOperation(value = "订单提交锁定库存", httpMethod = "POST")
    @ApiImplicitParam(name = "skuStockVO", value = "订单库存信息", required = true, paramType = "body", dataType = "WareSkuStockVO")
    @PostMapping("/batch/lock")
    public Result<Boolean> lockStock(@RequestBody WareSkuStockVO skuStockVO) {

        try {
            iPmsInventoryService.lockStock(skuStockVO);
            return Result.success();
        } catch (Exception e) {
            return Result.failed();
        }
    }


    @ApiOperation(value = "订单取消释放库存", httpMethod = "POST")
    @ApiImplicitParam(name = "skuStockVO", value = "订单库存信息", required = true, paramType = "body", dataType = "WareSkuStockVO")
    @PostMapping("/stock/release")
    public Result<Boolean> releaseStock(@RequestBody WareSkuStockVO skuStockVO) {

        try {
            iPmsInventoryService.releaseStock(skuStockVO);
            return Result.success();
        } catch (Exception e) {
            return Result.failed();
        }
    }


}
