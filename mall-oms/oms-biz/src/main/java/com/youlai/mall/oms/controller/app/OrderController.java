package com.youlai.mall.oms.controller.app;

import com.youlai.common.core.result.Result;
import com.youlai.common.mybatis.utils.PageUtils;
import com.youlai.mall.oms.pojo.entity.OrderEntity;
import com.youlai.mall.oms.pojo.vo.OrderConfirmVO;
import com.youlai.mall.oms.pojo.vo.OrderSubmitResultVO;
import com.youlai.mall.oms.pojo.vo.OrderSubmitVO;
import com.youlai.mall.oms.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutionException;


/**
 * 订单详情表
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
@Api(tags = "订单详情接口")
@RestController
@RequestMapping("/api.app/v1/orders")
@Slf4j
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    /**
     * 订单确认信息，生成订单
     * 如果入参传了skuId，则以当前skuId为准，商品数量默认为1
     * 如果没有传，则从购物车中获取数据
     * 如果购物车中没有数据，则返回为空
     *
     * @return
     */
    @ApiOperation(value = "订单确认信息", httpMethod = "POST")
    @PostMapping("/confirm")
    public Result<OrderConfirmVO> confirm(@RequestParam(value = "skuId",required = false) String skuId,
                                          @RequestParam(value = "number",defaultValue = "1") Integer number) {
        return Result.success(orderService.confirm(skuId,number));
    }

    @ApiOperation(value = "提交订单", httpMethod = "POST")
    @ApiImplicitParam(name = "submit", value = "提交订单参数", required = true, paramType = "body", dataType = "OrderSubmitVO")
    @PostMapping("/submit")
    public Result<OrderSubmitResultVO> submit(@Valid @RequestBody OrderSubmitVO submit) throws ExecutionException, InterruptedException {
        log.info("提交订单：{}", submit);
        return Result.success(orderService.submit(submit));
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("oms:order:list")
    public Result<PageUtils> list(@RequestParam Map<String, Object> params) {
        PageUtils page = orderService.queryPage(params);

        return Result.success(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @GlobalTransactional
    //@RequiresPermissions("oms:order:info")
    public Result<OrderEntity> info(@PathVariable("id") Long id) {
        OrderEntity order = orderService.getById(id);

        return Result.success(order);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("oms:order:save")
    public Result<Object> save(@RequestBody OrderEntity order) {
        orderService.save(order);

        return Result.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("oms:order:update")
    public Result<Object> update(@RequestBody OrderEntity order) {
        orderService.updateById(order);

        return Result.success();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("oms:order:delete")
    public Result<Object> delete(@RequestBody Long[] ids) {
        orderService.removeByIds(Arrays.asList(ids));

        return Result.success();
    }

}
