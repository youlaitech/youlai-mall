package com.youlai.mall.pms.controller.admin;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.domain.PmsAttribute;
import com.youlai.mall.pms.service.IPmsAttributeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "【系统管理】商品属性")
@RestController
@RequestMapping("/api.admin/v1/attributes")
@Slf4j
@AllArgsConstructor
public class AttributeController {

    private IPmsAttributeService iPmsAttributeService;

    @ApiOperation(value = "属性列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "商品分类ID", paramType = "query", dataType = "Long")
    })
    @GetMapping
    public Result list(Long categoryId) {
        List<PmsAttribute> list = iPmsAttributeService.list(new LambdaQueryWrapper<PmsAttribute>()
                .eq(PmsAttribute::getCategoryId, categoryId));
        return Result.success(list);
    }

    @ApiOperation(value = "批量新增")
    @ApiImplicitParam(name = "attributes", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsAttribute")
    @PostMapping("/batch")
    public Result saveBatch(@RequestBody List<PmsAttribute> attributes) {

        if (CollectionUtil.isEmpty(attributes)) {
            return Result.failed("请至少提交一条属性");
        }

        Long categoryId = attributes.get(0).getCategoryId();
        List<Long> formIds = attributes.stream().map(item -> item.getId()).collect(Collectors.toList());
        List<Long> dbIds = iPmsAttributeService
                .list(new LambdaQueryWrapper<PmsAttribute>()
                        .eq(PmsAttribute::getCategoryId, categoryId)
                        .select(PmsAttribute::getId))
                .stream()
                .map(item -> item.getId())
                .collect(Collectors.toList());

        // 提交删除
        if (CollectionUtil.isNotEmpty(dbIds)) {
            List<Long> removeIds = dbIds.stream()
                    .filter(id -> CollectionUtil.isEmpty(formIds) || !formIds.contains(id))
                    .collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(removeIds)) {
                iPmsAttributeService.removeByIds(removeIds);
            }
        }

        boolean result = iPmsAttributeService.saveOrUpdateBatch(attributes);
        return Result.judge(result);
    }
}
