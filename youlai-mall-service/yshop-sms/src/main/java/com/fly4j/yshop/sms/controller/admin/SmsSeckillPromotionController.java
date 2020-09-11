package com.fly4j.yshop.sms.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.core.controller.BaseController;
import com.fly4j.yshop.sms.pojo.entity.SmsSeckillPromotion;
import com.fly4j.yshop.sms.service.ISmsSeckillPromotionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "ADMIN-秒杀活动")
@RestController
@RequestMapping("/seckill/promotions")
@Slf4j
public class SmsSeckillPromotionController extends BaseController {

    @Resource
    private ISmsSeckillPromotionService iSmsSeckillPromotionService;

    @ApiOperation(value = "秒杀活动分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "Integer", defaultValue = "0"),
            @ApiImplicitParam(name = "limit", value = "每页数量", required = true, paramType = "path", dataType = "Integer", defaultValue = "10"),
            @ApiImplicitParam(name = "title", value = "活动标题", paramType = "query", dataType = "String"),
    })
    @GetMapping("/page/{page}/limit/{limit}")
    public R<Page<SmsSeckillPromotion>> page(
            @PathVariable Integer page,
            @PathVariable Integer limit,
            String title
    ) {
        Page<SmsSeckillPromotion> data = iSmsSeckillPromotionService.page(new Page<>(page, limit),
                new LambdaQueryWrapper<SmsSeckillPromotion>()
                        .like(StrUtil.isNotBlank(title), SmsSeckillPromotion::getTitle, title)
                        .orderByDesc(SmsSeckillPromotion::getCreate_time));
        return R.ok(data);
    }

    @ApiOperation(value = "秒杀活动列表", httpMethod = "GET")
    @GetMapping()
    public R list() {
        List<SmsSeckillPromotion> list = iSmsSeckillPromotionService.list();
        return R.ok(list);
    }

    @ApiOperation(value = "新增秒杀活动", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "smsSeckillPromotion", value = "实体JSON对象", required = true, paramType = "body", dataType = "SmsSeckillPromotion")
    })
    @PostMapping
    public R save(@RequestBody SmsSeckillPromotion smsSeckillPromotion) {
        boolean status = iSmsSeckillPromotionService.save(smsSeckillPromotion);
        return status ? R.ok(null) : R.failed("新增失败");
    }

    @ApiOperation(value = "秒杀活动详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "秒杀活动id", required = true, paramType = "path", dataType = "Integer"),
    })
    @GetMapping("/{id}")
    public R get(@PathVariable Integer id) {
        SmsSeckillPromotion user = iSmsSeckillPromotionService.getById(id);
        return R.ok(user);
    }

    @ApiOperation(value = "修改秒杀活动", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "秒杀活动id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "smsSeckillPromotion", value = "实体JSON对象", required = true, paramType = "body", dataType = "SmsSeckillPromotion")
    })
    @PutMapping(value = "/{id}")
    public R update(@PathVariable("id") Integer id, @RequestBody SmsSeckillPromotion smsSeckillPromotion) {
        boolean status = iSmsSeckillPromotionService.updateById(smsSeckillPromotion);
        return status ? R.ok(null) : R.failed("更新失败");
    }

    @ApiOperation(value = "删除秒杀活动", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "秒杀活动id", required = true, paramType = "query", allowMultiple = true, dataType = "Integer")
    @DeleteMapping()
    public R delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iSmsSeckillPromotionService.removeByIds(ids);
        return status ? R.ok(null) : R.failed("删除失败");
    }

    @ApiOperation(value = "修改秒杀活动状态", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "秒杀活动id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "status", value = "显示状态", required = true, paramType = "path", dataType = "Integer")
    })
    @PutMapping("/id/{id}/status/{status}")
    public R updateStatus(@PathVariable Integer id, @PathVariable Integer status) {
        boolean result = iSmsSeckillPromotionService.update(new LambdaUpdateWrapper<SmsSeckillPromotion>()
                .eq(SmsSeckillPromotion::getId, id)
                .set(SmsSeckillPromotion::getStatus, status));
        if (result) {
            return R.ok("更新成功");
        } else {
            return R.failed("更新失败");
        }
    }
}