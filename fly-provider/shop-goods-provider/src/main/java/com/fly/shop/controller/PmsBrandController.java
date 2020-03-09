package com.fly.shop.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly.common.core.domain.Result;
import com.fly.shop.pojo.entity.PmsBrand;
import com.fly.shop.services.IPmsBrandService;
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
public class PmsBrandController {
    @Resource
    private IPmsBrandService iPmsBrandService;

    @GetMapping("/pageNum/{pageNum}/pageSize/{pageSize}")
    public Result<Page<PmsBrand>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, PmsBrand pmsBrand) {
        Page<PmsBrand> page = new Page<>(pageNum, pageSize);
        Page<PmsBrand> data = (Page) iPmsBrandService.page(page, new LambdaQueryWrapper<PmsBrand>()
                .like(StringUtils.isNotBlank(pmsBrand.getBrandName()), PmsBrand::getBrandName, pmsBrand.getBrandName())
                .orderByDesc(PmsBrand::getSort));
        return Result.success(data);
    }
    @GetMapping("/list")
    public List<PmsBrand> getList() {
        List<PmsBrand> list = iPmsBrandService.findAll();
        return list;
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        PmsBrand pmsBrand = iPmsBrandService.getById(id);
        return Result.success(pmsBrand);
    }

    @PostMapping
    public Result add(@RequestBody PmsBrand pmsBrand) {
        boolean status = iPmsBrandService.save(pmsBrand);
        return Result.status(status);
    }

    @PutMapping(value = "/{id}")
    public Result update(@PathVariable("id") Long id, @RequestBody PmsBrand pmsBrand) {
        boolean status = iPmsBrandService.updateById(pmsBrand);
        return Result.status(status);
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable Long[] ids) {
        boolean status = iPmsBrandService.removeByIds(Arrays.asList(ids));
        return Result.status(status);
    }

}
