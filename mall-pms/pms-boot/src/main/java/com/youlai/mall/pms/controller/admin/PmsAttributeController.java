package com.youlai.mall.pms.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.form.PmsCategoryAttributeForm;
import com.youlai.mall.pms.pojo.entity.PmsCategoryAttribute;
import com.youlai.mall.pms.service.AttributeService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 「管理端」商品属性控制器
 *
 * @author <a href="mailto:1490493387@qq.com">haoxr</a>
 * @date 2022/1/1
 */
@Api(tags = "「管理端」商品属性")
@RestController
@RequestMapping("/api/v1/attributes")
@Slf4j
@AllArgsConstructor
public class PmsAttributeController {

    private AttributeService attributeService;

    @ApiOperation(value = "属性列表")
    @GetMapping
    public Result listAttributes(
            @ApiParam("商品分类ID") Long categoryId,
            @ApiParam("类型（1：规格；2：属性）") Integer type
    ) {
        List<PmsCategoryAttribute> list = attributeService.list(new LambdaQueryWrapper<PmsCategoryAttribute>()
                .eq(categoryId != null, PmsCategoryAttribute::getCategoryId, categoryId)
                .eq(type != null, PmsCategoryAttribute::getType, type)
        );
        return Result.success(list);
    }

    @ApiOperation(value = "批量新增/修改")
    @PostMapping("/batch")
    public Result saveBatch(@RequestBody PmsCategoryAttributeForm pmsCategoryAttributeForm) {
        boolean result = attributeService.saveBatch(pmsCategoryAttributeForm);
        return Result.judge(result);
    }
}
