package com.youlai.mall.oms.controller.app;

import com.youlai.common.core.result.Result;
import com.youlai.common.mybatis.utils.PageUtils;
import com.youlai.mall.oms.pojo.entity.OrderDeliveryEntity;
import com.youlai.mall.oms.service.OrderDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 订单物流记录表
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
@RestController
@RequestMapping("oms/orderdelivery")
public class OrderDeliveryController {
    @Autowired
    private OrderDeliveryService orderDeliveryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("oms:orderdelivery:list")
    public Result<PageUtils> list(@RequestParam Map<String, Object> params){
        PageUtils page = orderDeliveryService.queryPage(params);

        return Result.success(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("oms:orderdelivery:info")
    public Result<OrderDeliveryEntity> info(@PathVariable("id") Long id){
		OrderDeliveryEntity orderDelivery = orderDeliveryService.getById(id);

        return Result.success(orderDelivery);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("oms:orderdelivery:save")
    public Result<Object> save(@RequestBody OrderDeliveryEntity orderDelivery){
		orderDeliveryService.save(orderDelivery);

        return Result.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("oms:orderdelivery:update")
    public Result<Object> update(@RequestBody OrderDeliveryEntity orderDelivery){
		orderDeliveryService.updateById(orderDelivery);

        return Result.success();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("oms:orderdelivery:delete")
    public Result<Object> delete(@RequestBody Long[] ids){
		orderDeliveryService.removeByIds(Arrays.asList(ids));

        return Result.success();
    }

}
