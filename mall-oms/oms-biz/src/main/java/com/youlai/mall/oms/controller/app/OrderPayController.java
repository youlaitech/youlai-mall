package com.youlai.mall.oms.controller.app;

import com.youlai.common.core.result.Result;
import com.youlai.common.mybatis.utils.PageUtils;
import com.youlai.mall.oms.pojo.entity.OrderPayEntity;
import com.youlai.mall.oms.service.OrderPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 支付信息表
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
@RestController
@RequestMapping("oms/orderpay")
public class OrderPayController {
    @Autowired
    private OrderPayService orderPayService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("oms:orderpay:list")
    public Result<PageUtils> list(@RequestParam Map<String, Object> params){
        PageUtils page = orderPayService.queryPage(params);

        return Result.success(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("oms:orderpay:info")
    public Result<OrderPayEntity> info(@PathVariable("id") Long id){
		OrderPayEntity orderPay = orderPayService.getById(id);

        return Result.success(orderPay);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("oms:orderpay:save")
    public Result<Object> save(@RequestBody OrderPayEntity orderPay){
		orderPayService.save(orderPay);

        return Result.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("oms:orderpay:update")
    public Result<Object> update(@RequestBody OrderPayEntity orderPay){
		orderPayService.updateById(orderPay);

        return Result.success();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("oms:orderpay:delete")
    public Result<Object> delete(@RequestBody Long[] ids){
		orderPayService.removeByIds(Arrays.asList(ids));

        return Result.success();
    }

}
