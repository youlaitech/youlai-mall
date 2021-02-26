package com.youlai.mall.pms.controller.admin;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.domain.PmsCategoryAttr;
import com.youlai.mall.pms.service.IPmsCategoryAttrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "【系统管理】分类属性")

@RestController
@RequestMapping("/admin-api/v1/attrs")
@Slf4j
@AllArgsConstructor
public class AttrController {

    private IPmsCategoryAttrService iPmsCategoryAttrService;

    @ApiOperation(value = "分类属性列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "分类ID", paramType = "query", dataType = "Long")

    })
    @GetMapping
    public Result list(Long categoryId) {
        List<PmsCategoryAttr> list = iPmsCategoryAttrService
                .list(new LambdaQueryWrapper<PmsCategoryAttr>().eq(PmsCategoryAttr::getCategoryId, categoryId));
        return Result.success(list);
    }


    @ApiOperation(value = "新增属性", httpMethod = "POST")
    @ApiImplicitParam(name = "attrCategories", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsAttrCategory")
    @PostMapping
    public Result saveBatch(@RequestBody List<PmsCategoryAttr> attrCategories) {

        if (CollectionUtil.isEmpty(attrCategories)) {
            return Result.failed("至少提交一条属性");
        }

        Long categoryId = attrCategories.get(0).getCategoryId();

        List<Long> formIds = attrCategories.stream().map(item -> item.getId()).collect(Collectors.toList());

        List<Long> databaseIds = iPmsCategoryAttrService
                .list(new LambdaQueryWrapper<PmsCategoryAttr>()
                        .eq(PmsCategoryAttr::getCategoryId, categoryId)
                        .select(PmsCategoryAttr::getId)
                ).stream()
                .map(item -> item.getId())
                .collect(Collectors.toList());

        // 删除的商品分类属性
        if (CollectionUtil.isNotEmpty(databaseIds)) {
            List<Long> removeIds = databaseIds.stream()
                    .filter(id -> CollectionUtil.isEmpty(formIds) || !formIds.contains(id))
                    .collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(removeIds)) {
                iPmsCategoryAttrService.removeByIds(removeIds);
            }
        }

        boolean result = iPmsCategoryAttrService.saveOrUpdateBatch(attrCategories);
        return Result.judge(result);
    }
}
