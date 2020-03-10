package com.fly.shop.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly.common.core.domain.Result;
import com.fly.shop.pojo.entity.GoodsBrand;
import com.fly.shop.services.IGoodsBrandService;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: Mr.
 * @create: 2020-03-05 17:04
 **/
@RestController
@RequestMapping("/brands")
public class GoodsBrandController {
    @Resource
    private IGoodsBrandService iGoodsBrandService;

    @GetMapping("/pageNum/{pageNum}/pageSize/{pageSize}")
    public Result<Page<GoodsBrand>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, GoodsBrand goodsBrand) {
        Page<GoodsBrand> page = new Page<>(pageNum, pageSize);
        Page<GoodsBrand> data = (Page) iGoodsBrandService.page(page, new LambdaQueryWrapper<GoodsBrand>()
                .like(StringUtils.isNotBlank(goodsBrand.getBrandName()), GoodsBrand::getBrandName, goodsBrand.getBrandName())
                .orderByDesc(GoodsBrand::getSort));
        return Result.success(data);
    }
    @GetMapping("/list")
    public List<GoodsBrand> getList() {
        List<GoodsBrand> list = iGoodsBrandService.findAll();
        return list;
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        GoodsBrand GoodsBrand = iGoodsBrandService.getById(id);
        return Result.success(GoodsBrand);
    }

    @PostMapping
    public Result add(@RequestBody GoodsBrand goodsBrand) {
        boolean status = iGoodsBrandService.save(goodsBrand);
        return Result.status(status);
    }

    @PutMapping(value = "/{id}")
    public Result update(@PathVariable("id") Long id, @RequestBody GoodsBrand goodsBrand) {
        boolean status = iGoodsBrandService.updateById(goodsBrand);
        return Result.status(status);
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable Long[] ids) {
        boolean status = iGoodsBrandService.removeByIds(Arrays.asList(ids));
        return Result.status(status);
    }

}
