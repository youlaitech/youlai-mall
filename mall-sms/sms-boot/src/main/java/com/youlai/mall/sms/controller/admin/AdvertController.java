package com.youlai.mall.sms.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.mall.sms.pojo.SmsAdvert;
import com.youlai.mall.sms.service.ISmsAdvertService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Api(tags = "「系统端」营销广告")
@RestController("AdminAdvertController")
@RequestMapping("/api/v1/adverts")
@Slf4j
@RequiredArgsConstructor
public class AdvertController {

    private final ISmsAdvertService iSmsAdvertService;

    @ApiOperation(value = "列表分页")
    @GetMapping
    public PageResult getAdvertPageList(
            @ApiParam("页码") Long pageNum,
            @ApiParam("每页数量") Long pageSize,
            @ApiParam("广告标题") String title
    ) {
        Page<SmsAdvert> result = iSmsAdvertService.page(new Page<>(pageNum, pageSize),
                new LambdaQueryWrapper<SmsAdvert>()
                        .like(StrUtil.isNotBlank(title), SmsAdvert::getTitle, StrUtil.isNotBlank(title) ? title : null)
                        .orderByAsc(SmsAdvert::getSort)
                        .orderByDesc(SmsAdvert::getGmtModified)
        );
        return PageResult.success(result);
    }

    @ApiOperation(value = "广告详情")
    @GetMapping("/{id}")
    public Result getAdvertDetail(
            @ApiParam("广告ID") @PathVariable Long id) {
        SmsAdvert advert = iSmsAdvertService.getById(id);
        return Result.success(advert);
    }

    @ApiOperation(value = "新增广告")
    @PostMapping
    public Result addAvert(@RequestBody SmsAdvert advert) {
        boolean status = iSmsAdvertService.save(advert);
        return Result.judge(status);
    }

    @ApiOperation(value = "修改广告")
    @PutMapping(value = "/{id}")
    public Result updateAdvert(
            @ApiParam("广告ID") @PathVariable Long id,
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

    @ApiOperation(value = "选择性更新广告")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long")
    @PatchMapping(value = "/{id}")
    public Result patch(@PathVariable Integer id, @RequestBody SmsAdvert advert) {
        LambdaUpdateWrapper<SmsAdvert> updateWrapper = new LambdaUpdateWrapper<SmsAdvert>().eq(SmsAdvert::getId, id);
        updateWrapper.set(advert.getStatus() != null, SmsAdvert::getStatus, advert.getStatus());
        boolean result = iSmsAdvertService.update(updateWrapper);
        return Result.judge(result);
    }
}
