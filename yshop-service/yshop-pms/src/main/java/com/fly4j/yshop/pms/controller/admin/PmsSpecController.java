package com.fly4j.yshop.pms.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.yshop.common.api.PageResult;
import com.fly4j.yshop.common.api.Result;
import com.fly4j.yshop.common.core.controller.BaseController;
import com.fly4j.yshop.pms.pojo.entity.PmsSpec;
import com.fly4j.yshop.pms.service.IPmsSpecService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "ADMIN-商品规格")
@RestController
@RequestMapping("/spec")
public class PmsSpecController extends BaseController {

    @Resource
    private IPmsSpecService iPmsSpecService;

    @ApiOperation(value = "规格列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "name", value = "规格名称", paramType = "query", dataType = "String"),
    })
    @GetMapping
    public Result list(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) String name) {
        LambdaQueryWrapper<PmsSpec> queryWrapper = new LambdaQueryWrapper<PmsSpec>()
                .eq(StrUtil.isNotBlank(name), PmsSpec::getName, name)
                .orderByDesc(PmsSpec::getCreate_time);

        if (page != null && limit != null) {
            // 分页查询
            Page<PmsSpec> result = iPmsSpecService.page(new Page<>(page, limit), queryWrapper);
            return PageResult.ok(result.getRecords(), result.getTotal());
        } else if (limit != null) {
            // 限制条数查询
            queryWrapper.last("limit " + limit);
        }
        List<PmsSpec> list = iPmsSpecService.list(queryWrapper);
        return Result.ok(list);
    }

    @ApiOperation(value = "规格详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        PmsSpec spec = iPmsSpecService.getById(id);
        return Result.ok(spec);
    }

    @ApiOperation(value = "新增规格", httpMethod = "POST")
    @ApiImplicitParam(name = "spec", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsSpec")
    @PostMapping
    public Result save(@RequestBody PmsSpec spec) {
        boolean status = iPmsSpecService.save(spec);
        return Result.status(status);
    }

    @ApiOperation(value = "修改规格", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "spec", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsSpec")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable("id") Long id,
            @RequestBody PmsSpec spec
    ) {
        boolean status = iPmsSpecService.updateById(spec);
        return Result.status(status);
    }

    @ApiOperation(value = "删除规格", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "id集合", required = true, paramType = "query", allowMultiple = true, dataType = "Long")
    @DeleteMapping
    public Result delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iPmsSpecService.removeByIds(ids);
        return Result.status(status);
    }

}
