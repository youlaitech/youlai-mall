package com.fly4j.yshop.pms.feign.admin;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.yshop.pms.feign.admin.factory.PmsFeignFallbackFactory;
import com.fly4j.yshop.pms.pojo.dto.admin.PmsSkuDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "youlai-mall-pms", fallbackFactory = PmsFeignFallbackFactory.class)
public interface PmsFeign {

    @GetMapping(value = "/spus/{id}")
    R getSpuById(@PathVariable Long id);


    @GetMapping("/skus/page/{page}/limit/{limit}")
     R<Page<PmsSkuDTO>> skuPage(@PathVariable Integer page,
                                @PathVariable Integer limit,
                                @RequestParam(required = false) String spu_name);

}
