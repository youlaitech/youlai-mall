package com.youlai.mall.pms.api;

import com.youlai.common.core.result.Result;
import com.youlai.mall.pms.pojo.dto.SkuDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("mall-pms")
public interface ProductFeignService {

    /**
     * 获取商品信息
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

}
