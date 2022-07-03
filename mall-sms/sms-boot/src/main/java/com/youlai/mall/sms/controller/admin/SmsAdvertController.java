package com.youlai.mall.sms.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.mall.sms.pojo.entity.SmsAdvert;
import com.youlai.mall.sms.pojo.query.AdvertPageQuery;
import com.youlai.mall.sms.service.SmsAdvertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Api(tags = "「管理端」营销广告")
@RestController
@RequestMapping("/api/v1/adverts")
@RequiredArgsConstructor
public class SmsAdvertController {

    private final SmsAdvertService smsAdvertService;

    @ApiOperation(value = "广告分页列表")
    @GetMapping("/pages")
    public PageResult listAdvertPages(AdvertPageQuery queryParams) {

        // 查询参数
        int pageNum = queryParams.getPageNum();
        int pageSize = queryParams.getPageSize();
        String keywords = queryParams.getKeywords();

        // 分页查询
        Page<SmsAdvert> result = smsAdvertService.page(
                new Page<>(pageNum, pageSize),
                new LambdaQueryWrapper<SmsAdvert>()
                        .like(StrUtil.isNotBlank(keywords), SmsAdvert::getTitle, keywords)
                        .orderByAsc(SmsAdvert::getSort)
        );
        return PageResult.success(result);
    }

    @ApiOperation(value = "广告详情")
    @GetMapping("/{id}")
    public Result getAdvertDetail(
            @ApiParam("广告ID") @PathVariable Long id
    ) {
        SmsAdvert advert = smsAdvertService.getById(id);
        return Result.success(advert);
    }

    @ApiOperation(value = "新增广告")
    @PostMapping
    public Result addAvert(@RequestBody SmsAdvert advert) {
        boolean status = smsAdvertService.save(advert);
        return Result.judge(status);
    }

    @ApiOperation(value = "修改广告")
    @PutMapping(value = "/{id}")
    public Result updateAdvert(
            @ApiParam("广告ID") @PathVariable Long id,
            @RequestBody SmsAdvert advert) {
        boolean status = smsAdvertService.updateById(advert);
        return Result.judge(status);
    }

    @ApiOperation(value = "删除广告")
    @DeleteMapping("/{ids}")
    public Result deleteAdverts(@ApiParam("广告ID，多个以英文逗号(,)分割") @PathVariable("ids") String ids) {
        boolean status = smsAdvertService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.judge(status);


    }

}
