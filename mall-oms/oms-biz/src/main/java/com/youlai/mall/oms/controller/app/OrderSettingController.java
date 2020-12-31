package com.youlai.mall.oms.controller.app;

import com.youlai.common.core.result.Result;
import com.youlai.common.mybatis.utils.PageUtils;
import com.youlai.mall.oms.pojo.entity.OrderSettingEntity;
import com.youlai.mall.oms.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 订单配置信息
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
@RestController
@RequestMapping("oms/ordersetting")
public class OrderSettingController {
    @Autowired
    private OrderSettingService orderSettingService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("oms:ordersetting:list")
    public Result<PageUtils> list(@RequestParam Map<String, Object> params){
        PageUtils page = orderSettingService.queryPage(params);

        return Result.success(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("oms:ordersetting:info")
    public Result<OrderSettingEntity> info(@PathVariable("id") Long id){
		OrderSettingEntity orderSetting = orderSettingService.getById(id);

        return Result.success(orderSetting);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("oms:ordersetting:save")
    public Result<Object> save(@RequestBody OrderSettingEntity orderSetting){
		orderSettingService.save(orderSetting);

        return Result.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("oms:ordersetting:update")
    public Result<Object> update(@RequestBody OrderSettingEntity orderSetting){
		orderSettingService.updateById(orderSetting);

        return Result.success();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("oms:ordersetting:delete")
    public Result<Object> delete(@RequestBody Long[] ids){
		orderSettingService.removeByIds(Arrays.asList(ids));

        return Result.success();
    }

}
