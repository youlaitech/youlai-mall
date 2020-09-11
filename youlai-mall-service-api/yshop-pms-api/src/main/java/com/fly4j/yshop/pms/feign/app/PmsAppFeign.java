package com.fly4j.yshop.pms.feign.app;

import com.baomidou.mybatisplus.extension.api.R;
import com.fly4j.yshop.pms.feign.app.factory.PmsAppFeignFallbackFactory;
import com.fly4j.yshop.pms.pojo.entity.PmsSku;
import com.fly4j.yshop.pms.pojo.vo.SkuLockVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "youlai-mall-pms", fallbackFactory = PmsAppFeignFallbackFactory.class)
public interface PmsAppFeign {

    @GetMapping(value = "/api.app/v1/skus/{id}")
    R<PmsSku> getSkuById(@PathVariable Integer id);

    @PostMapping(value = "/api.app/v1/skus/lock",consumes = MediaType.APPLICATION_JSON_VALUE)
    R<SkuLockVO> checkAndLockStock(@RequestBody List<SkuLockVO> skuLockVOS);

    @PutMapping(value = "/api.app/v1/skus/minus")
    Integer minusStock(@RequestParam Long sku_id, @RequestParam Integer sku_quantity);
}
