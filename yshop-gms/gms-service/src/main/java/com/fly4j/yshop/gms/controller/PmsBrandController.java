package com.fly4j.yshop.gms.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.common.core.controller.BaseController;
import com.fly4j.yshop.gms.pojo.entity.PmsAttribute;
import com.fly4j.yshop.gms.pojo.entity.PmsGoods;
import com.fly4j.yshop.gms.service.IPmsGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/gms/brands")
@Slf4j
public class PmsBrandController extends BaseController {

    @Resource
    private IPmsGoodsService iPmsGoodsService;

    @GetMapping("/pageNum/{pageNum}/pageSize/{pageSize}")
    public R<Page<PmsGoods>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, PmsGoods pmsGoods) {
        Page<PmsGoods> page = new Page<>(pageNum, pageSize);
        Page<PmsGoods> data = (Page<PmsGoods>) iPmsGoodsService.page(page, new LambdaQueryWrapper<PmsGoods>()
                .eq(StrUtil.isNotBlank(pmsGoods.getName()), PmsGoods::getName, pmsGoods.getName())
                .orderByDesc(PmsGoods::getCreateTime));
        return R.ok(data);
    }

    @GetMapping()
    public R list() {
        List<PmsGoods> list = iPmsGoodsService.list();
        return R.ok(list);
    }

    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        PmsGoods user = iPmsGoodsService.getById(id);
        return R.ok(user);
    }

    @PostMapping
    public R add(@RequestBody PmsGoods PmsGoods) {
        boolean status = iPmsGoodsService.save(PmsGoods);
        return status ? R.ok(null) : R.failed("新增失败");
    }

    @PutMapping(value = "/{id}")
    public R update(@PathVariable("id") Long id, @RequestBody PmsGoods PmsGoods) {
        boolean status = iPmsGoodsService.updateById(PmsGoods);
        return status ? R.ok(null) : R.failed("更新失败");
    }

    @DeleteMapping("/{ids}")
    public R delete(@PathVariable Long[] ids) {
        boolean status = iPmsGoodsService.removeByIds(Arrays.asList(ids));
        return status ? R.ok(null) : R.failed("删除失败");
    }

}
