package com.fly4j.yshop.pms.feign;

import com.baomidou.mybatisplus.extension.api.R;
import com.fly4j.yshop.pms.feign.factory.PmsAppFeignFallbackFactory;
import com.fly4j.yshop.pms.pojo.entity.PmsSku;
import com.fly4j.yshop.pms.pojo.vo.SkuLockVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "yshop-pms", fallbackFactory = PmsAppFeignFallbackFactory.class)
public interface PmsAppFeign {

    @GetMapping(value = "/api.app/v1/skus/{id}")
    R<PmsSku> getSkuById(@PathVariable Long id);

    @PostMapping(value = "/api.app/v1/skus/lock",consumes = MediaType.APPLICATION_JSON_VALUE)
    R<SkuLockVO> checkAndLockStock(@RequestBody List<SkuLockVO> skuLockVOS);
}
