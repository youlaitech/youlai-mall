package com.youlai.mall.pms.controller.app;

import cn.hutool.core.bean.BeanUtil;
import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.domain.PmsSku;
import com.youlai.mall.pms.pojo.dto.InventoryDTO;
import com.youlai.mall.pms.pojo.dto.InventoryNumDTO;
import com.youlai.mall.pms.service.IPmsSkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
    @ApiImplicitParam(name = "id", value = "商品库存ID", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result<InventoryDTO> detail(@PathVariable Long id) {
        PmsSku sku = iPmsSkuService.getById(id);
        InventoryDTO InventoryDTO = new InventoryDTO();
        BeanUtil.copyProperties(sku, InventoryDTO);
        return Result.success(InventoryDTO);
    }

    @ApiOperation("获取商品的库存数量")
    @ApiImplicitParam(name = "id", value = "商品库存ID", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}/inventory")
    public Result<Integer> getInventoryById(@PathVariable Long id) {
        Integer inventory = iPmsSkuService.getInventoryById(id);
        return Result.success(inventory);
    }


    @ApiOperation(value = "锁定库存", httpMethod = "PUT")
    @ApiImplicitParam(name = "list", value = "锁定库存", required = true, paramType = "body", dataType = "InventoryNumDTO")
    @PostMapping("/batch/_lock")
    public Result<Boolean> lockInventory(@RequestBody List<InventoryNumDTO> list) {
        boolean result = iPmsSkuService.lockInventory(list);
        return Result.judge(result);
    }


    @ApiOperation(value = "解锁库存", httpMethod = "PUT")
    @ApiImplicitParam(name = "list", value = "释放库存", required = true, paramType = "body", dataType = "InventoryNumDTO")
    @PostMapping("/batch/_unlock")
    public Result<Boolean> unlockInventory(@RequestBody List<InventoryNumDTO> list) {
        boolean result = iPmsSkuService.unlockInventory(list);
        return Result.judge(result);
    }


    @ApiOperation(value = "库存列表", httpMethod = "GET")
    @ApiImplicitParam(name = "skuIds", value = "库存ID集合字符串，英文逗号,分割", required = true, paramType = "param", dataType = "String")
    @GetMapping("/{ids}")
    public Result list(@PathVariable String ids) {
        List<InventoryDTO> list = iPmsSkuService.listBySkuIds(ids);
        return Result.success(list);
    }

}
