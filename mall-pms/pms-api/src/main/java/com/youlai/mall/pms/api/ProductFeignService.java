package com.youlai.mall.pms.api;

import com.youlai.common.core.result.Result;
import com.youlai.mall.pms.pojo.dto.SkuDTO;
import com.youlai.mall.pms.pojo.vo.SkuInfoVO;
import com.youlai.mall.pms.pojo.vo.WareSkuStockVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("mall-pms")
public interface ProductFeignService {

    /**
     * 批量获取商品详情
     *
     * @param skuIds
     * @return
     */
    @GetMapping("/api.app/v1/sku/infos")
    Result<List<SkuInfoVO>> infos(@RequestParam("skuId") List<String> skuIds);

    /**
     * 获取商品信息
     *
     * @param id
     * @return
     */
    @GetMapping("/api.app/v1/sku/{id}")
    Result<SkuDTO> getSkuById(@PathVariable Long id);

    /**
     * 修改商品库存
     */
    @PutMapping("/api.admin/v1/sku/{id}/stock")
    Result updateStock(@PathVariable Long id, @RequestParam Integer num);

    /**
     * 订单下单锁定库存
     *
     * @param skuStockVO
     * @return
     */
    @PostMapping("/api.app/v1/sku/stock/lock")
    Result lockStock(@RequestBody WareSkuStockVO skuStockVO);

}
