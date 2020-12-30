package com.youlai.mall.oms.controller.app;

import com.youlai.common.core.result.Result;
import com.youlai.common.mybatis.utils.PageUtils;
import com.youlai.mall.oms.pojo.entity.OrderEntity;
import com.youlai.mall.oms.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 订单详情表
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
@RestController
@RequestMapping("oms/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("oms:order:list")
    public Result<PageUtils> list(@RequestParam Map<String, Object> params){
        PageUtils page = orderService.queryPage(params);

        return Result.success(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("oms:order:info")
    public Result<OrderEntity> info(@PathVariable("id") Long id){
		OrderEntity order = orderService.getById(id);

        return Result.success(order);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("oms:order:save")
    public Result<Object> save(@RequestBody OrderEntity order){
		orderService.save(order);

        return Result.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("oms:order:update")
    public Result<Object> update(@RequestBody OrderEntity order){
		orderService.updateById(order);

        return Result.success();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("oms:order:delete")
    public Result<Object> delete(@RequestBody Long[] ids){
		orderService.removeByIds(Arrays.asList(ids));

        return Result.success();
    }

}
