package com.youlai.mall.pms.controller.admin;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youlai.common.core.result.Result;
import com.youlai.mall.pms.pojo.PmsAttrCategory;
import com.youlai.mall.pms.service.IPmsAttrCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "属性分类接口")
@RestController
@RequestMapping("/admin-api/v1/attr-categories")
@Slf4j
@AllArgsConstructor
public class AdminAttrCategoryController {

    private IPmsAttrCategoryService iPmsAttrCategoryService;

    @ApiOperation(value = "属性分类列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "分类ID", paramType = "query", dataType = "Long")
    })
    @GetMapping
    public Result list(Long categoryId) {
        List<PmsAttrCategory> list = iPmsAttrCategoryService
                .list(new LambdaQueryWrapper<PmsAttrCategory>().eq(PmsAttrCategory::getCategoryId, categoryId));
        return Result.success(list);
    }


    @ApiOperation(value = "新增属性", httpMethod = "POST")
    @ApiImplicitParam(name = "attrCategories", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsAttrCategory")
    @PostMapping
    public Result saveBatch(@RequestBody List<PmsAttrCategory> attrCategories) {

        if (CollectionUtil.isEmpty(attrCategories)) {
            return Result.failed("至少提交一条属性");
        }

        Long categoryId = attrCategories.get(0).getCategoryId();


        List<Long> formIds = attrCategories.stream().map(item -> item.getId()).collect(Collectors.toList());

        List<Long> databaseIds = iPmsAttrCategoryService
                .list(new LambdaQueryWrapper<PmsAttrCategory>()
                        .eq(PmsAttrCategory::getCategoryId, categoryId)
                        .select(PmsAttrCategory::getId)
                ).stream()
                .map(item -> item.getId())
                .collect(Collectors.toList());

        // 删除的商品分类属性
        if (CollectionUtil.isNotEmpty(databaseIds)) {
            List<Long> removeIds = databaseIds.stream()
                    .filter(id -> CollectionUtil.isEmpty(formIds) || !formIds.contains(id))
                    .collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(removeIds)) {
                iPmsAttrCategoryService.removeByIds(removeIds);
            }
        }

        boolean result = iPmsAttrCategoryService.saveOrUpdateBatch(attrCategories);
        return Result.status(result);
    }
}
