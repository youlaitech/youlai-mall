package com.fly.shop.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly.shop.pojo.entity.PmsBrand;
import com.fly.shop.services.IPmsBrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
    public Page<PmsBrand> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, PmsBrand pmsBrand) {
        Page<PmsBrand> page = new Page<>(pageNum, pageSize);
        Page<PmsBrand> data = (Page) iPmsBrandService.page(page);
        return data;
    }
    @GetMapping("/list")
    public List<PmsBrand> getList() {
        List<PmsBrand> list = iPmsBrandService.findAll();
        return list;
    }
}
