package com.fly.shop.controller;

import com.fly.common.core.domain.Result;
import com.fly.shop.pojo.dto.GoodsDTO;
import com.fly.shop.services.IGoodsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description:
 * @author: Mr.
 * @create: 2020-03-13 17:04
 **/
@RestController
@RequestMapping("/add")
public class GoodsController {
    @Resource
    private IGoodsService iGoodsService;

    @PostMapping
    public Result add(@RequestBody GoodsDTO goodsDto) {
        boolean status = iGoodsService.add(goodsDto);
        return Result.status(status);
    }

}
