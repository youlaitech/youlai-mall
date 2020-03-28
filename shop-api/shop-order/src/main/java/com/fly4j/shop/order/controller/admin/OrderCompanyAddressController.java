package com.fly4j.shop.order.controller.admin;

import com.baomidou.mybatisplus.extension.api.R;
import com.fly4j.shop.order.pojo.entity.OrderCompanyAddress;
import com.fly4j.shop.order.service.IOrderCompanyAddressService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/orders/companyAddress")
public class OrderCompanyAddressController {

    @Resource
    private IOrderCompanyAddressService iOrderCompanyAddressService;

    @GetMapping("/list")
    public R list(){
        List<OrderCompanyAddress> list = iOrderCompanyAddressService.list();
        return R.ok(list);
    }

    @PutMapping("/{id}")
    public R update(@PathVariable("id") Integer id, @RequestBody OrderCompanyAddress orderReturnApply) {
        boolean result = iOrderCompanyAddressService.updateById(orderReturnApply);
        if (result) {
            return R.ok("更新成功");
        } else {
            return R.failed("更新失败");
        }
    }

}
