package com.fly.shop.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly.common.core.domain.Result;
import com.fly.shop.pojo.dto.GoodsDTO;
import com.fly.shop.pojo.entity.Goods;
import com.fly.shop.services.IGoodsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @description:
 * @author: Mr.
 * @create: 2020-03-13 17:04
 **/
@RestController
@RequestMapping
public class GoodsController {
    @Resource
    private IGoodsService iGoodsService;

    @GetMapping("/pageNum/{pageNum}/pageSize/{pageSize}")
    public Result<Page<Goods>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, Goods goods) {
        Page<Goods> page = new Page<>(pageNum, pageSize);
        Page<Goods> data = (Page) iGoodsService.page(page, new LambdaQueryWrapper<Goods>());
        return Result.success(data);
    }

    @PostMapping("/add")
    public Result add(@RequestBody GoodsDTO goodsDto) {
        boolean status = iGoodsService.add(goodsDto);
        return Result.status(status);
    }

}
