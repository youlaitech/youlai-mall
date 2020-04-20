package com.fly4j.yshop.pms.controller.app;

import com.baomidou.mybatisplus.extension.api.R;
import com.fly4j.common.core.controller.BaseController;
import com.fly4j.yshop.pms.pojo.entity.PmsSku;
import com.fly4j.yshop.pms.pojo.vo.SkuLockVO;
import com.fly4j.yshop.pms.service.IPmsSkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api.app/v1/skus")
@Slf4j
public class AppSkuController extends BaseController {
    @Resource
    private IPmsSkuService iPmsSkuService;

    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        PmsSku sku = iPmsSkuService.getById(id);
        return R.ok(sku);
    }

    @PostMapping("/lock")
    public R checkAndLockStock(@RequestBody List<SkuLockVO> skuLockVOS) {
        String msg = iPmsSkuService.checkAndLockStock(skuLockVOS);
        if (StringUtils.isNotBlank(msg)) {
            return R.failed(msg);
        }
        return R.ok(null);
    }

    @PutMapping(value = "/minus")
    public Integer minusStock(@RequestParam Long sku_id,@RequestParam Integer sku_quantity){
        return iPmsSkuService.minusStock(sku_id,sku_quantity);
    }

}
