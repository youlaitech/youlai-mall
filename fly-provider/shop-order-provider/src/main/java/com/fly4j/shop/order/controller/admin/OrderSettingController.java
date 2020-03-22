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

    @GetMapping("/{id}")
    public R get(@PathVariable("id") Integer id) {
        OrderSetting orderSetting = iOrderSettingService.getById(id);
        return R.ok(orderSetting);
    }


    @PutMapping("/{id}")
    public R update(@PathVariable("id") Integer id, OrderSetting orderSetting) {
        boolean result = iOrderSettingService.updateById(orderSetting);
        if (result) {
            return R.ok("更新成功");
        } else {
            return R.failed("更新失败");
        }
    }
}
