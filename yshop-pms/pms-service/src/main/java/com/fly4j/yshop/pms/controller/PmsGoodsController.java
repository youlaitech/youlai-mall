package com.fly4j.yshop.pms.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.common.core.controller.BaseController;
import com.fly4j.yshop.pms.pojo.entity.PmsGoods;
import com.fly4j.yshop.pms.service.IPmsGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Api(tags = "商品接口")
@RestController
@RequestMapping("/goods")
@Slf4j
public class PmsGoodsController extends BaseController {

    @Resource
    private IPmsGoodsService iPmsGoodsService;

    @ApiOperation(value = "商品分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "Integer", defaultValue = "0"),
            @ApiImplicitParam(name = "limit", value = "每页数量", required = true, paramType = "path", dataType = "Integer", defaultValue = "10"),
            @ApiImplicitParam(name = "name", value = "商品名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "goods_sn", value = "每页数量", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "is_new", value = "每页数量", paramType = "query", dataType = "Integer"),
    })
    @GetMapping("/page/{page}/limit/{limit}")
    public R<Page<PmsGoods>> page(
            @PathVariable Integer page,
            @PathVariable Integer limit,
            String name,
            String goods_sn,
            String is_new
    ) {
        Page<PmsGoods> data = (Page<PmsGoods>) iPmsGoodsService.page(new Page<>(page, limit),
                new LambdaQueryWrapper<PmsGoods>()
                        .like(StrUtil.isNotBlank(name), PmsGoods::getName, name)
                        .like(StrUtil.isNotBlank(goods_sn), PmsGoods::getGoods_sn, goods_sn)
                        .eq(is_new != null, PmsGoods::getIs_new, is_new)
                        .orderByDesc(PmsGoods::getCreate_time));
        return R.ok(data);
    }

    @ApiOperation(value = "获取商品列表", httpMethod = "GET")
    @GetMapping()
    public R list() {
        List<PmsGoods> list = iPmsGoodsService.list();
        return R.ok(list);
    }


    @ApiOperation(value = "新增商品", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pmsGoods", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsGoods")
    })
    @PostMapping
    public R save(@RequestBody PmsGoods PmsGoods) {
        boolean status = iPmsGoodsService.save(PmsGoods);
        return status ? R.ok(null) : R.failed("上架商品失败");
    }

    @ApiOperation(value = "商品详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品ID", required = true, paramType = "path", dataType = "Long"),
    })
    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        PmsGoods user = iPmsGoodsService.getById(id);
        return R.ok(user);
    }


    @ApiOperation(value = "修改商品", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "pmsGoods", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsGoods")
    })
    @PutMapping(value = "/{id}")
    public R update(@PathVariable("id") Long id, @RequestBody PmsGoods pmsGoods) {
        boolean status = iPmsGoodsService.updateById(pmsGoods);
        return status ? R.ok(null) : R.failed("更新商品失败");
    }


    @ApiOperation(value = "删除商品", httpMethod = "delete")
    @ApiImplicitParam(name = "ids", value = "商品ID", required = true, paramType = "query", allowMultiple = true, dataType = "Long")
    @DeleteMapping()
    public R delete(@PathVariable Long[] ids) {
        boolean status = iPmsGoodsService.removeByIds(Arrays.asList(ids));
        return status ? R.ok(null) : R.failed("删除失败");
    }

}
