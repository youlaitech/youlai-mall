package com.fly4j.yshop.pms.controller.app;

import com.baomidou.mybatisplus.extension.api.R;
import com.fly4j.common.core.controller.BaseController;
import com.fly4j.yshop.pms.pojo.entity.PmsSku;
import com.fly4j.yshop.pms.service.IPmsSkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api.app/v1/skus")
@Slf4j
public class SkuController extends BaseController {
    @Resource
    private IPmsSkuService iPmsSkuService;

    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        PmsSku sku = iPmsSkuService.getById(id);
        return R.ok(sku);
    }
}
