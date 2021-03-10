package com.youlai.mall.pms.controller.admin;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.domain.PmsSpecification;
import com.youlai.mall.pms.service.IPmsSpecificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "【系统管理】商品规格")
@RestController
@RequestMapping("/api.admin/v1/specifications")
@Slf4j
@AllArgsConstructor
public class SpecificationController {

    private IPmsSpecificationService iPmsSpecificationService;

    @ApiOperation(value = "分类规格列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "分类ID", paramType = "query", dataType = "Long")
    })
    @GetMapping
    public Result list(Long categoryId) {
        List<PmsSpecification> list = iPmsSpecificationService
                .list(new LambdaQueryWrapper<PmsSpecification>()
                        .eq(PmsSpecification::getCategoryId, categoryId));
        return Result.success(list);
    }

    @ApiOperation(value = "新增规格", httpMethod = "POST")
    @ApiImplicitParam(name = "specCategories", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsSpecCategory")
    @PostMapping
    public Result save(@RequestBody List<PmsSpecification> specCategories) {

        if (CollectionUtil.isEmpty(specCategories)) {
            return Result.failed("至少提交一条规格");
        }

        Long categoryId = specCategories.get(0).getCategoryId();


        List<Long> formIds = specCategories.stream().map(item -> item.getId()).collect(Collectors.toList());

        List<Long> databaseIds = iPmsSpecificationService
                .list(new LambdaQueryWrapper<PmsSpecification>()
                        .eq(PmsSpecification::getCategoryId, categoryId)
                        .select(PmsSpecification::getId)
                ).stream()
                .map(item -> item.getId())
                .collect(Collectors.toList());

        // 删除的商品分类规格
        if (CollectionUtil.isNotEmpty(databaseIds)) {
            List<Long> removeIds = databaseIds.stream()
                    .filter(id -> CollectionUtil.isEmpty(formIds) || !formIds.contains(id))
                    .collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(removeIds)) {
                iPmsSpecificationService.removeByIds(removeIds);
            }
        }
        boolean result = iPmsSpecificationService.saveOrUpdateBatch(specCategories);
        return Result.judge(result);
    }
}
