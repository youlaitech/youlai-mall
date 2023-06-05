package com.youlai.mall.sms.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.mall.sms.model.entity.SmsAdvert;
import com.youlai.mall.sms.model.query.AdvertPageQuery;
import com.youlai.mall.sms.service.SmsAdvertService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Tag(name = "「管理端」营销广告")
@RestController
@RequestMapping("/api/v1/adverts")
@RequiredArgsConstructor
public class SmsAdvertController {

    private final SmsAdvertService smsAdvertService;

    @Operation(summary= "广告分页列表")
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

    @Operation(summary= "广告详情")
    @GetMapping("/{id}")
    public Result getAdvertDetail(
            @Parameter(name = "广告ID") @PathVariable Long id
    ) {
        SmsAdvert advert = smsAdvertService.getById(id);
        return Result.success(advert);
    }

    @Operation(summary= "新增广告")
    @PostMapping
    public Result addAvert(@RequestBody SmsAdvert advert) {
        boolean status = smsAdvertService.save(advert);
        return Result.judge(status);
    }

    @Operation(summary= "修改广告")
    @PutMapping(value = "/{id}")
    public Result updateAdvert(
            @Parameter(name = "广告ID") @PathVariable Long id,
            @RequestBody SmsAdvert advert) {
        boolean status = smsAdvertService.updateById(advert);
        return Result.judge(status);
    }

    @Operation(summary= "删除广告")
    @DeleteMapping("/{ids}")
    public Result deleteAdverts(@Parameter(name = "广告ID，多个以英文逗号(,)分割") @PathVariable("ids") String ids) {
        boolean status = smsAdvertService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.judge(status);


    }

}
