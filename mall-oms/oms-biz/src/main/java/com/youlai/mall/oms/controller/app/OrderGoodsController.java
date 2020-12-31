package com.youlai.mall.oms.controller.app;

import com.youlai.common.core.result.Result;
import com.youlai.common.mybatis.utils.PageUtils;
import com.youlai.mall.oms.pojo.entity.OrderGoodsEntity;
import com.youlai.mall.oms.service.OrderGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 订单商品信息表
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
@RestController
@RequestMapping("oms/ordergoods")
public class OrderGoodsController {
    @Autowired
    private OrderGoodsService orderGoodsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("oms:ordergoods:list")
    public Result<PageUtils> list(@RequestParam Map<String, Object> params){
        PageUtils page = orderGoodsService.queryPage(params);

        return Result.success(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("oms:ordergoods:info")
    public Result<OrderGoodsEntity> info(@PathVariable("id") Long id){
		OrderGoodsEntity orderGoods = orderGoodsService.getById(id);

        return Result.success(orderGoods);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("oms:ordergoods:save")
    public Result<Object> save(@RequestBody OrderGoodsEntity orderGoods){
		orderGoodsService.save(orderGoods);

        return Result.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("oms:ordergoods:update")
    public Result<Object> update(@RequestBody OrderGoodsEntity orderGoods){
		orderGoodsService.updateById(orderGoods);

        return Result.success();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("oms:ordergoods:delete")
    public Result<Object> delete(@RequestBody Long[] ids){
		orderGoodsService.removeByIds(Arrays.asList(ids));

        return Result.success();
    }

}
