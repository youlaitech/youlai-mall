package com.youlai.mall.pms.api;

import com.youlai.common.web.config.FeignDecoderConfig;
import com.youlai.mall.pms.pojo.dto.SkuInfoDTO;
import com.youlai.mall.pms.pojo.dto.LockedSkuDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(value = "mall-pms", contextId = "sku", configuration = {FeignDecoderConfig.class})
public interface SkuFeignClient {

    /**
     * 获取商品库存单元信息
     */
    @GetMapping("/app-api/v1/sku/{skuId}")
    SkuInfoDTO getSkuInfo(@PathVariable Long skuId);

    /**
     * 获取商品库存信息列表
     *
     * @param skuIds SKU ID 列表
     * @return 商品库存信息列表
     */
    @GetMapping("/app-api/v1/skus")
    List<SkuInfoDTO> getSkuInfoList(@RequestParam List<Long> skuIds);

    /**
     * 锁定商品库存
     */
    @PutMapping("/app-api/v1/skus/lock")
    boolean lockStock(@RequestParam String orderToken, @RequestBody List<LockedSkuDTO> lockedSkuList);

    /**
     * 解锁商品库存
     */
    @PutMapping("/app-api/v1/sku/unlock")
    boolean unlockStock(@RequestParam String orderSn);

    /**
     * 扣减订单商品库存
     * <p>
     * PUT /app-api/v1/sku/deduct
     * <p>
     * 扣减指定订单商品的库存数量。
     *
     * @param orderSn 订单编号
     * @return 扣减库存结果
     */
    @PutMapping("/app-api/v1/sku/deduct")
    boolean deductStock(@RequestParam String orderSn);

}
