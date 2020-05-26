package com.fly4j.yshop.pms.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.yshop.common.api.PageResult;
import com.fly4j.yshop.common.api.Result;
import com.fly4j.yshop.common.core.controller.BaseController;
import com.fly4j.yshop.pms.pojo.entity.PmsAttribute;
import com.fly4j.yshop.pms.service.IPmsAttributeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Api(tags = "ADMIN-商品属性")
@RestController
@RequestMapping("/attributes")
public class PmsAttributeController extends BaseController {

    @Resource
    private IPmsAttributeService iPmsAttributeService;

    @ApiOperation(value = "品牌分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "name", value = "品牌名称", paramType = "query", dataType = "String"),
    })
    @GetMapping
    public Result list(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) String name
    ) {
        LambdaQueryWrapper<PmsAttribute> queryWrapper = new LambdaQueryWrapper<PmsAttribute>()
                .eq(StrUtil.isNotBlank(name), PmsAttribute::getName, name)
                .orderByDesc(PmsAttribute::getCreate_time);

        if (page != null && limit != null) {
            Page<PmsAttribute> result = iPmsAttributeService.page(new Page<>(page, limit), queryWrapper);
            return PageResult.ok(result.getRecords(), result.getTotal());
        } else if (limit != null) {
            queryWrapper.last("LIMIT " + limit);
        }
        List<PmsAttribute> list = iPmsAttributeService.list(queryWrapper);
        return Result.ok(list);
    }

    @ApiOperation(value = "属性详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "属性id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        PmsAttribute attribute = iPmsAttributeService.getById(id);
        return Result.ok(attribute);
    }

    @ApiOperation(value = "新增属性", httpMethod = "POST")
    @ApiImplicitParam(name = "attribute", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsAttribute")
    @PostMapping
    public Result add(@RequestBody PmsAttribute attribute) {
        boolean status = iPmsAttributeService.save(attribute);
        return Result.status(status);
    }


    @ApiOperation(value = "修改属性", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "属性id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "attribute", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsAttribute")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable("id") Long id,
            @RequestBody PmsAttribute attribute) {
        boolean status = iPmsAttributeService.updateById(attribute);
        return Result.status(status);
    }

    @ApiOperation(value = "删除属性", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "id集合", required = true, paramType = "query", allowMultiple = true, dataType = "Long")
    @DeleteMapping
    public Result delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iPmsAttributeService.removeByIds(ids);
        return Result.status(status);
    }

}
