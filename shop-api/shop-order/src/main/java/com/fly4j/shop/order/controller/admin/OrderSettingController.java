package com.fly4j.shop.order.controller.admin;

import com.baomidou.mybatisplus.extension.api.R;
import com.fly4j.shop.order.pojo.entity.OrderSetting;
import com.fly4j.shop.order.service.IOrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/setting")
public class OrderSettingController {

    @Autowired
    private IOrderSettingService iOrderSettingService;

    @GetMapping
    public R get() {
        OrderSetting orderSetting = iOrderSettingService.getOne(null);
        return R.ok(orderSetting);
    }

    @PostMapping()
    public R add( @RequestBody OrderSetting orderSetting) {
        boolean result = iOrderSettingService.save(orderSetting);
        if (result) {
            return R.ok("新增成功");
        } else {
            return R.failed("新增失败");
        }
    }

    @PutMapping("/{id}")
    public R update(@PathVariable("id") Integer id, @RequestBody  OrderSetting orderSetting) {
        boolean result = iOrderSettingService.updateById(orderSetting);
        if (result) {
            return R.ok("更新成功");
        } else {
            return R.failed("更新失败");
        }
    }
}
