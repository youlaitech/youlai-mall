package com.youlai.mall.sms.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.result.Result;
import com.youlai.mall.sms.pojo.SmsAdvert;
import com.youlai.mall.sms.service.ISmsAdvertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Api(tags = "【系统管理】营销广告")
@RestController("AdminAdvertController")
@RequestMapping("/api/v1/adverts")
@Slf4j
@AllArgsConstructor
public class AdvertController {

    private ISmsAdvertService iSmsAdvertService;

    @ApiOperation(value = "列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "name", value = "广告名称", paramType = "query", dataType = "String")
    })
    @GetMapping
    public Result list(
            Integer page,
            Integer limit,
            String name) {
        LambdaQueryWrapper<SmsAdvert> queryWrapper = new LambdaQueryWrapper<SmsAdvert>()
                .like(StrUtil.isNotBlank(name), SmsAdvert::getName, name)
                .orderByAsc(SmsAdvert::getSort)
                .orderByDesc(SmsAdvert::getGmtModified)
                .orderByDesc(SmsAdvert::getGmtCreate);

        Page<SmsAdvert> result = iSmsAdvertService.page(new Page<>(page, limit), queryWrapper);
        return Result.success(result.getRecords(), result.getTotal());
    }

    @ApiOperation(value = "广告详情")
    @ApiImplicitParam(name = "id", value = "广告id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        SmsAdvert advert = iSmsAdvertService.getById(id);
        return Result.success(advert);
    }

    @ApiOperation(value = "新增广告")
    @PostMapping
    public Result add(@RequestBody SmsAdvert advert) {
        boolean status = iSmsAdvertService.save(advert);
        return Result.judge(status);
    }

    @ApiOperation(value = "修改广告")
    @ApiImplicitParam(name = "id", value = "广告id", required = true, paramType = "path", dataType = "Long")
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Integer id,
            @RequestBody SmsAdvert advert) {
        boolean status = iSmsAdvertService.updateById(advert);
        return Result.judge(status);
    }

    @ApiOperation(value = "删除广告")
    @ApiImplicitParam(name = "ids", value = "id集合", required = true, paramType = "query", dataType = "String")
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable("ids") String ids) {
        boolean status = iSmsAdvertService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.judge(status);
    }

    @ApiOperation(value = "修改广告(选择性更新)")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long")
    @PatchMapping(value = "/{id}")
    public Result patch(@PathVariable Integer id, @RequestBody SmsAdvert advert) {
        LambdaUpdateWrapper<SmsAdvert> updateWrapper = new LambdaUpdateWrapper<SmsAdvert>().eq(SmsAdvert::getId, id);
        updateWrapper.set(advert.getStatus() != null, SmsAdvert::getStatus, advert.getStatus());
        boolean result = iSmsAdvertService.update(updateWrapper);
        return Result.judge(result);
    }
}
