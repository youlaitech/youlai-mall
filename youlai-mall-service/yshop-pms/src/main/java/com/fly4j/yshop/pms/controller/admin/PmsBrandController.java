package com.fly4j.yshop.pms.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.api.PageResult;
import com.youlai.common.api.Result;
import com.youlai.common.core.controller.BaseController;
import com.fly4j.yshop.pms.pojo.entity.PmsBrand;
import com.fly4j.yshop.pms.service.IPmsBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@Api(tags = "ADMIN-商品品牌")
@RestController
@RequestMapping("/brands")
@Slf4j
public class PmsBrandController extends BaseController {

    @Resource
    private IPmsBrandService iPmsBrandService;

    @ApiOperation(value = "品牌分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "name", value = "品牌名称", paramType = "query", dataType = "String"),
    })
    @GetMapping
    public Result list( Integer page,Integer limit,String name) {
        LambdaQueryWrapper<PmsBrand> queryWrapper = new LambdaQueryWrapper<PmsBrand>()
                .eq(StrUtil.isNotBlank(name), PmsBrand::getName, name)
                .orderByAsc(PmsBrand::getSort)
                .orderByDesc(PmsBrand::getCreate_time);

        if (page != null && limit != null) {
            Page<PmsBrand> result = iPmsBrandService.page(new Page<>(page, limit), queryWrapper);
            return PageResult.ok(result.getRecords(), result.getTotal());
        } else if (limit != null) {
            queryWrapper.last("LIMIT " + limit);
        }
        List<PmsBrand> list = iPmsBrandService.list(queryWrapper);
        return Result.ok(list);
    }


    @ApiOperation(value = "新增品牌", httpMethod = "POST")
    @ApiImplicitParam(name = "brand", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsBrand")
    @PostMapping
    public Result save(@RequestBody PmsBrand brand) {
        boolean status = iPmsBrandService.save(brand);
        return Result.status(status);
    }

    @ApiOperation(value = "品牌详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "品牌id", required = true, paramType = "path", dataType = "Integer")
    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        PmsBrand brand = iPmsBrandService.getById(id);
        return Result.ok(brand);
    }

    @ApiOperation(value = "修改品牌", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "品牌id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "brand", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsBrand")
    })
    @PutMapping(value = "/{id}")
    public Result update(@PathVariable("id") Long id,
                         @RequestBody PmsBrand brand) {
        boolean status = iPmsBrandService.updateById(brand);
        return Result.status(status);
    }

    @ApiOperation(value = "删除品牌", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "id集合", required = true, paramType = "query", allowMultiple = true, dataType = "Integer")
    @DeleteMapping()
    public Result delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iPmsBrandService.removeByIds(ids);
        return Result.status(status);
    }

}