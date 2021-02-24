package com.youlai.mall.oms.controller.app;

import com.youlai.common.mybatis.utils.PageUtils;
import com.youlai.common.result.Result;
import com.youlai.common.utils.EnumUtils;
import com.youlai.mall.oms.enums.OrderPayTypeEnum;
import com.youlai.mall.oms.pojo.entity.OrderPayEntity;
import com.youlai.mall.oms.pojo.form.OrderPayForm;
import com.youlai.mall.oms.pojo.vo.PayInfoVO;
import com.youlai.mall.oms.service.OrderPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 订单支付服务
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
@Api(tags = "订单支付服务")
@RestController
@RequestMapping("/api.app/v1/order/pay")
@Slf4j
public class OrderPayController {
    @Autowired
    private OrderPayService orderPayService;

    /**
     * 订单支付
     * 1、根据支付类型选择正确支付方式（1：微信支付；2：支付宝支付；3：余额支付）
     * 2、根据订单id查询订单价格，进行支付（在整个支付的过程中进行事务控制，保证整个操作的原子性）
     * 3、将支付结果记录日志并返回给前端
     *
     * @param orderPayForm 订单支付表单
     * @return
     */
    @ApiOperation("订单支付")
    @PostMapping
    public Result doPay(@Validated @RequestBody OrderPayForm orderPayForm) {
        OrderPayTypeEnum payTypeEnum = EnumUtils.getByCode(orderPayForm.getPayType(), OrderPayTypeEnum.class);
        if (payTypeEnum == null) {
            return Result.failed("请选择正确的支付方式");
        }
        log.info("订单支付，orderId={}，支付方式={}", orderPayForm.getOrderId(), payTypeEnum.desc);
        if (payTypeEnum == OrderPayTypeEnum.BALANCE) {
            orderPayService.balancePay(orderPayForm.getOrderId());
        }
        return Result.success();
    }

    @ApiOperation(value = "获取订单支付详情")
    @GetMapping("/info")
    public Result<PayInfoVO> info(@ApiParam(name = "orderId", value = "订单ID", required = true, defaultValue = "1") @RequestParam("orderId") String orderId) {
        return Result.success(orderPayService.info(orderId));
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public Result<PageUtils> list(@RequestParam Map<String, Object> params) {
        PageUtils page = orderPayService.queryPage(params);

        return Result.success(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("oms:orderpay:info")
    public Result<OrderPayEntity> info(@PathVariable("id") Long id) {
        OrderPayEntity orderPay = orderPayService.getById(id);

        return Result.success(orderPay);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("oms:orderpay:save")
    public Result<Object> save(@RequestBody OrderPayEntity orderPay) {
        orderPayService.save(orderPay);

        return Result.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("oms:orderpay:update")
    public Result<Object> update(@RequestBody OrderPayEntity orderPay) {
        orderPayService.updateById(orderPay);

        return Result.success();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("oms:orderpay:delete")
    public Result<Object> delete(@RequestBody Long[] ids) {
        orderPayService.removeByIds(Arrays.asList(ids));

        return Result.success();
    }

}
