package com.youlai.mall.sale.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.mall.sale.model.entity.Advert;
import com.youlai.mall.sale.model.query.AdvertPageQuery;
import com.youlai.mall.sale.service.AdvertService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * 营销广告
 *
 * @author Ray.Hao
 * @since 1.0.0
 */
@Tag(name = "Admin-营销广告")
@RestController
@RequestMapping("/api/v1/adverts")
@RequiredArgsConstructor
public class AdvertController {

    private final AdvertService advertService;

    @Operation(summary = "广告分页列表")
    @GetMapping("/page")
    public PageResult<Advert> getAdvertPage(AdvertPageQuery queryParams) {

        // 查询参数
        int pageNum = queryParams.getPageNum();
        int pageSize = queryParams.getPageSize();
        String keywords = queryParams.getKeywords();

        // 分页查询
        Page<Advert> result = advertService.page(
                new Page<>(pageNum, pageSize),
                new LambdaQueryWrapper<Advert>()
                        .like(StrUtil.isNotBlank(keywords), Advert::getTitle, keywords)
                        .orderByAsc(Advert::getSort)
        );
        return PageResult.success(result);
    }

    @Operation(summary = "广告详情")
    @GetMapping("/{id}")
    public Result getAdvertDetail(
            @Parameter(description = "广告ID") @PathVariable Long id
    ) {
        Advert advert = advertService.getById(id);
        return Result.success(advert);
    }

    @Operation(summary = "新增广告")
    @PostMapping
    public Result addAvert(@RequestBody Advert advert) {
        boolean status = advertService.save(advert);
        return Result.judge(status);
    }

    @Operation(summary = "修改广告")
    @PutMapping(value = "/{id}")
    public Result updateAdvert(
            @Parameter(description = "广告ID") @PathVariable Long id,
            @RequestBody Advert advert) {
        boolean status = advertService.updateById(advert);
        return Result.judge(status);
    }

    @Operation(summary = "删除广告")
    @DeleteMapping("/{ids}")
    public Result deleteAdverts(@Parameter(description = "广告ID，多个以英文逗号(,)分割") @PathVariable("ids") String ids) {
        boolean status = advertService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.judge(status);
    }

}
