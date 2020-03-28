package com.fly4j.shop.order.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.shop.order.pojo.entity.OrderReturnApply;
import com.fly4j.shop.order.service.IOrderReturnApplyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders/returnApply")
public class OrderReturnApplyController {

    @Autowired
    private IOrderReturnApplyService iOrderReturnApplyService;

    @GetMapping("/pageNum/{pageNum}/pageSize/{pageSize}")
    public R<Page<OrderReturnApply>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, OrderReturnApply orderReturnApply) {
        Page<OrderReturnApply> page = new Page<>(pageNum, pageSize);
        Page<OrderReturnApply> data = (Page) iOrderReturnApplyService.page(page,
                new LambdaQueryWrapper<OrderReturnApply>()
                        .like(StringUtils.isNotBlank(orderReturnApply.getOrderNo()),
                                OrderReturnApply::getOrderNo,
                                orderReturnApply.getOrderNo())
                        .orderByDesc(OrderReturnApply::getCreateTime)
        );
        return R.ok(data);
    }


    @GetMapping("/{id}")
    public R get(@PathVariable("id") Integer id) {
        OrderReturnApply orderReturnApply = iOrderReturnApplyService.getById(id);
        return R.ok(orderReturnApply);
    }

    @PostMapping
    public R add(@RequestBody OrderReturnApply orderReturnApply) {
        boolean result = iOrderReturnApplyService.save(orderReturnApply);
        if (result) {
            return R.ok("新增成功");
        } else {
            return R.failed("新增失败");
        }
    }

    @PutMapping("/{id}")
    public R update(@PathVariable("id") Integer id, @RequestBody OrderReturnApply orderReturnApply) {
        boolean result = iOrderReturnApplyService.updateById(orderReturnApply);
        if (result) {
            return R.ok("更新成功");
        } else {
            return R.failed("更新失败");
        }
    }

    @PutMapping("/id/{id}/status/{status}")
    public R updateStatus(@PathVariable Integer id, @PathVariable Integer status) {
        boolean result = iOrderReturnApplyService.update(new LambdaUpdateWrapper<OrderReturnApply>()
                .eq(OrderReturnApply::getId, id)
                .set(OrderReturnApply::getStatus, status));
        if (result) {
            return R.ok("更新成功");
        } else {
            return R.failed("更新失败");
        }
    }
}
