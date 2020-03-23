package com.fly4j.shop.order.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.shop.order.pojo.entity.OrderReturnReason;
import com.fly4j.shop.order.service.IOrderReturnReasonService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/returnReason")
public class OrderReturnReasonController {

    @Autowired
    private IOrderReturnReasonService iOrderReturnReasonService;

    @GetMapping("/pageNum/{pageNum}/pageSize/{pageSize}")
    public R<Page<OrderReturnReason>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, OrderReturnReason orderReturnReason) {
        Page<OrderReturnReason> page = new Page<>(pageNum, pageSize);
        Page<OrderReturnReason> data = (Page) iOrderReturnReasonService.page(page,
                new LambdaQueryWrapper<OrderReturnReason>()
                        .like(StringUtils.isNotBlank(orderReturnReason.getName()),
                                OrderReturnReason::getName,
                                orderReturnReason.getName())
                .orderByAsc(OrderReturnReason::getSort)
        );
        return R.ok(data);
    }


    @GetMapping("/{id}")
    public R get(@PathVariable("id") Integer id) {
        OrderReturnReason orderReturnReason = iOrderReturnReasonService.getById(id);
        return R.ok(orderReturnReason);
    }

    @PostMapping
    public R add(@RequestBody OrderReturnReason orderReturnReason) {
        boolean result = iOrderReturnReasonService.save(orderReturnReason);
        if (result) {
            return R.ok("新增成功");
        } else {
            return R.failed("新增失败");
        }
    }

    @PutMapping("/{id}")
    public R update(@PathVariable("id") Integer id,@RequestBody OrderReturnReason orderReturnReason) {
        boolean result = iOrderReturnReasonService.updateById(orderReturnReason);
        if (result) {
            return R.ok("更新成功");
        } else {
            return R.failed("更新失败");
        }
    }

    @PutMapping("/id/{id}/status/{status}")
    public R updateStatus(@PathVariable Integer id, @PathVariable Integer status) {
        boolean result = iOrderReturnReasonService.update(new LambdaUpdateWrapper<OrderReturnReason>()
                .eq(OrderReturnReason::getId, id)
                .set(OrderReturnReason::getStatus, status));
        if (result) {
            return R.ok("更新成功");
        } else {
            return R.failed("更新失败");
        }
    }
}
