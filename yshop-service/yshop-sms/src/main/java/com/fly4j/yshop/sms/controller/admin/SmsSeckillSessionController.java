package com.fly4j.yshop.sms.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.yshop.common.core.controller.BaseController;
import com.fly4j.yshop.sms.pojo.entity.SmsSeckillSession;
import com.fly4j.yshop.sms.service.ISmsSeckillSessionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "秒杀活动时间段API")
@RestController
@RequestMapping("/seckill/sessions")
@Slf4j
public class SmsSeckillSessionController extends BaseController {

    @Resource
    private ISmsSeckillSessionService iSmsSeckillSessionService;

    @ApiOperation(value = "秒杀活动时间段分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "Integer", defaultValue = "0"),
            @ApiImplicitParam(name = "limit", value = "每页数量", required = true, paramType = "path", dataType = "Integer", defaultValue = "10"),
            @ApiImplicitParam(name = "name", value = "时间段名称", paramType = "query", dataType = "String"),
    })
    @GetMapping("/page/{page}/limit/{limit}")
    public R<Page<SmsSeckillSession>> page(
            @PathVariable Integer page,
            @PathVariable Integer limit,
            String name
    ) {
        Page<SmsSeckillSession> data = iSmsSeckillSessionService.page(new Page<>(page, limit),
                new LambdaQueryWrapper<SmsSeckillSession>()
                        .like(StrUtil.isNotBlank(name), SmsSeckillSession::getName, name)
                        .orderByDesc(SmsSeckillSession::getCreate_time));
        return R.ok(data);
    }

    @ApiOperation(value = "秒杀活动时间段列表", httpMethod = "GET")
    @GetMapping()
    public R list() {
        List<SmsSeckillSession> list = iSmsSeckillSessionService.list();
        return R.ok(list);
    }

    @ApiOperation(value = "新增秒杀活动时间段", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "smsSeckillSession", value = "实体JSON对象", required = true, paramType = "body", dataType = "SmsSeckillSession")
    })
    @PostMapping
    public R save(@RequestBody SmsSeckillSession smsSeckillSession) {
        boolean status = iSmsSeckillSessionService.save(smsSeckillSession);
        return status ? R.ok(null) : R.failed("新增失败");
    }

    @ApiOperation(value = "秒杀活动时间段详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "秒杀活动时间段id", required = true, paramType = "path", dataType = "Long"),
    })
    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        SmsSeckillSession user = iSmsSeckillSessionService.getById(id);
        return R.ok(user);
    }

    @ApiOperation(value = "修改秒杀活动时间段", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "秒杀活动时间段id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "smsSeckill", value = "实体JSON对象", required = true, paramType = "body", dataType = "SmsSeckill")
    })
    @PutMapping(value = "/{id}")
    public R update(@PathVariable("id") Long id, @RequestBody SmsSeckillSession smsSeckillsession) {
        boolean status = iSmsSeckillSessionService.updateById(smsSeckillsession);
        return status ? R.ok(null) : R.failed("更新失败");
    }

    @ApiOperation(value = "删除秒杀活动时间段", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "秒杀活动时间段id", required = true, paramType = "query", allowMultiple = true, dataType = "Long")
    @DeleteMapping()
    public R delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iSmsSeckillSessionService.removeByIds(ids);
        return status ? R.ok(null) : R.failed("删除失败");
    }

    @ApiOperation(value = "修改秒杀活动时间段状态", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "秒杀活动时间段id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "status", value = "显示状态", required = true, paramType = "path", dataType = "Integer")
    })
    @PutMapping("/id/{id}/status/{status}")
    public R updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        boolean result = iSmsSeckillSessionService.update(new LambdaUpdateWrapper<SmsSeckillSession>()
                .eq(SmsSeckillSession::getId, id)
                .set(SmsSeckillSession::getStatus, status));
        if (result) {
            return R.ok("更新成功");
        } else {
            return R.failed("更新失败");
        }
    }
}