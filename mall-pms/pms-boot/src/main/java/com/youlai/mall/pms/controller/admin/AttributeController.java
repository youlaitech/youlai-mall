package com.youlai.mall.pms.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.dto.admin.AttributeFormDTO;
import com.youlai.mall.pms.pojo.entity.PmsAttribute;
import com.youlai.mall.pms.service.IPmsAttributeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 「系统端」商品属性控制器
 *
 * @author <a href="mailto:1490493387@qq.com">haoxr</a>
 * @date 2022/1/1
 */
@Api(tags = "「系统端」商品属性")
@RestController
@RequestMapping("/api/v1/attributes")
@Slf4j
@AllArgsConstructor
public class AttributeController {

    private IPmsAttributeService iPmsAttributeService;

    @ApiOperation(value = "属性列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "分类ID", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "type", value = "属性类型（1：规格；2：属性）", paramType = "query", dataType = "Integer"),
    })
    @GetMapping
    public Result list(Long categoryId, Integer type) {
        List<PmsAttribute> list = iPmsAttributeService.list(new LambdaQueryWrapper<PmsAttribute>()
                .eq(categoryId != null, PmsAttribute::getCategoryId, categoryId)
                .eq(type != null, PmsAttribute::getType, type)
        );
        return Result.success(list);
    }

    @ApiOperation(value = "批量新增/修改")
    @ApiImplicitParam(name = "attributeForm", value = "实体JSON对象", required = true, paramType = "body", dataType = "AttributeFormDTO")
    @PostMapping("/batch")
    public Result saveBatch(@RequestBody AttributeFormDTO attributeForm) {
        boolean result = iPmsAttributeService.saveBatch(attributeForm);
        return Result.judge(result);
    }
}
