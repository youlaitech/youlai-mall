package com.fly4j.yshop.pms.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.enums.ApiErrorCode;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.common.core.controller.BaseController;
import com.fly4j.yshop.pms.pojo.entity.PmsGoods;
import com.fly4j.yshop.pms.service.IPmsGoodsService;
import feign.Request;
import io.swagger.annotations.*;
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


    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "pmsGoods", value = "商品对象", required = true, paramType = "body", dataType = "PmsGoods")
    })
    @GetMapping("/pageNum/{pageNum}/pageSize/{pageSize}")
    public R<Page<PmsGoods>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, PmsGoods pmsGoods) {
        Page<PmsGoods> page = new Page<>(pageNum, pageSize);
        Page<PmsGoods> data = (Page<PmsGoods>) iPmsGoodsService.page(page, new LambdaQueryWrapper<PmsGoods>()
                .eq(StrUtil.isNotBlank(pmsGoods.getName()), PmsGoods::getName, pmsGoods.getName())
                .orderByDesc(PmsGoods::getCreateTime));
        return R.ok(data);
    }

    @ApiOperation(value = "获取商品列表", notes = "获取商品列表", httpMethod = "GET")
    @GetMapping()
    public R list() {
        List<PmsGoods> list = iPmsGoodsService.list();
        return R.ok(list);
    }


    @ApiOperation(value = "查询商品详情", notes = "根据商品ID查询商品详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品ID", required = true, paramType = "path", dataType = "Long"),
    })
    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        PmsGoods user = iPmsGoodsService.getById(id);
        return R.ok(user);
    }


    @ApiOperation(value = "上架商品", notes = "上架商品", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pmsGoods", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsGoods")
    })
    @PostMapping
    public R add(@RequestBody PmsGoods PmsGoods) {
        boolean status = iPmsGoodsService.save(PmsGoods);
        return status ? R.ok(null) : R.failed("上架商品失败");
    }


    @ApiOperation(value = "修改商品", notes = "修改商品", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "pmsGoods", value = "商品对象", required = true, paramType = "body", dataType = "PmsGoods")
    })
    @PutMapping(value = "/{id}")
    public R update(@PathVariable("id") Long id, @RequestBody PmsGoods pmsGoods) {
        boolean status = iPmsGoodsService.updateById(pmsGoods);
        return status ? R.ok(null) : R.failed("更新商品失败");
    }

    @DeleteMapping("/{ids}")
    public R delete(@PathVariable Long[] ids) {
        boolean status = iPmsGoodsService.removeByIds(Arrays.asList(ids));
        return status ? R.ok(null) : R.failed("删除失败");
    }

}
