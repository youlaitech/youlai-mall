package com.youlai.mall.oms.controller.app;

import com.youlai.common.core.result.Result;
import com.youlai.common.mybatis.utils.PageUtils;
import com.youlai.mall.oms.pojo.entity.OrderLogsEntity;
import com.youlai.mall.oms.service.OrderLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 订单操作历史记录
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
@RestController
@RequestMapping("oms/orderlogs")
public class OrderLogsController {
    @Autowired
    private OrderLogsService orderLogsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("oms:orderlogs:list")
    public Result<PageUtils> list(@RequestParam Map<String, Object> params){
        PageUtils page = orderLogsService.queryPage(params);

        return Result.success(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("oms:orderlogs:info")
    public Result<OrderLogsEntity> info(@PathVariable("id") Long id){
		OrderLogsEntity orderLogs = orderLogsService.getById(id);

        return Result.success(orderLogs);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("oms:orderlogs:save")
    public Result<Object> save(@RequestBody OrderLogsEntity orderLogs){
		orderLogsService.save(orderLogs);

        return Result.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("oms:orderlogs:update")
    public Result<Object> update(@RequestBody OrderLogsEntity orderLogs){
		orderLogsService.updateById(orderLogs);

        return Result.success();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("oms:orderlogs:delete")
    public Result<Object> delete(@RequestBody Long[] ids){
		orderLogsService.removeByIds(Arrays.asList(ids));

        return Result.success();
    }

}
