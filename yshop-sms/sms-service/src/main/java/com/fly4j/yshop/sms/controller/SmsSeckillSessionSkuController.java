package com.fly4j.yshop.sms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.common.core.controller.BaseController;
import com.fly4j.yshop.sms.pojo.entity.SmsSeckillSessionSku;
import com.fly4j.yshop.sms.service.ISmsSeckillSessionSkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "秒杀活动时间段商品接口")
@RestController
@RequestMapping("/seckill/skus")
@Slf4j
public class SmsSeckillSessionSkuController extends BaseController {

    @Resource
    private ISmsSeckillSessionSkuService iSmsSeckillSessionSkuService;

    @ApiOperation(value = "秒杀活动时间段商品分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "Integer", defaultValue = "0"),
            @ApiImplicitParam(name = "limit", value = "每页数量", required = true, paramType = "path", dataType = "Integer", defaultValue = "10"),
            @ApiImplicitParam(name = "title", value = "活动标题", paramType = "query", dataType = "String"),
    })
    @GetMapping("/page/{page}/limit/{limit}")
    public R<Page<SmsSeckillSessionSku>> page(
            @PathVariable Integer page,
            @PathVariable Integer limit,
            String title
    ) {
        Page<SmsSeckillSessionSku> data = (Page<SmsSeckillSessionSku>) iSmsSeckillSessionSkuService.page(new Page<>(page, limit),
                new LambdaQueryWrapper<SmsSeckillSessionSku>()
                        .orderByDesc(SmsSeckillSessionSku::getCreate_time));
        return R.ok(data);
    }

    @ApiOperation(value = "秒杀活动时间段商品列表", httpMethod = "GET")
    @GetMapping()
    public R list() {
        List<SmsSeckillSessionSku> list = iSmsSeckillSessionSkuService.list();
        return R.ok(list);
    }

    @ApiOperation(value = "新增秒杀活动时间段商品", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "smsSeckillSessionSku", value = "实体JSON对象", required = true, paramType = "body", dataType = "SmsSeckill")
    })
    @PostMapping
    public R save(@RequestBody SmsSeckillSessionSku smsSeckillSessionSku) {
        boolean status = iSmsSeckillSessionSkuService.save(smsSeckillSessionSku);
        return status ? R.ok(null) : R.failed("新增失败");
    }

    @ApiOperation(value = "秒杀活动时间段商品详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "秒杀活动时间段商品id", required = true, paramType = "path", dataType = "Long"),
    })
    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        SmsSeckillSessionSku user = iSmsSeckillSessionSkuService.getById(id);
        return R.ok(user);
    }

    @ApiOperation(value = "修改秒杀活动时间段商品", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "秒杀活动时间段商品id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "smsSeckillSessionSku", value = "实体JSON对象", required = true, paramType = "body", dataType = "SmsSeckill")
    })
    @PutMapping(value = "/{id}")
    public R update(@PathVariable("id") Long id, @RequestBody SmsSeckillSessionSku smsSeckillSessionSku) {
        boolean status = iSmsSeckillSessionSkuService.updateById(smsSeckillSessionSku);
        return status ? R.ok(null) : R.failed("更新失败");
    }

    @ApiOperation(value = "删除秒杀活动时间段商品", httpMethod = "delete")
    @ApiImplicitParam(name = "ids", value = "秒杀活动时间段商品id", required = true, paramType = "query", allowMultiple = true, dataType = "Long")
    @DeleteMapping()
    public R delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iSmsSeckillSessionSkuService.removeByIds(ids);
        return status ? R.ok(null) : R.failed("删除失败");
    }

}