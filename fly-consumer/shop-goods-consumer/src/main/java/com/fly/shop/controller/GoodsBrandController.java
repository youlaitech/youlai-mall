package com.fly.shop.controller;

import com.fly.shop.domain.GoodsBrand;
import com.fly.shop.feign.IGoodsBrandService;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/shop/brand")
public class GoodsBrandController {
    @Resource
    private IGoodsBrandService iGoodsBrandService;

    @GetMapping("/list")
    public List<GoodsBrand> getList() {
        List<GoodsBrand> list = iGoodsBrandService.getAll();
        return list;
    }
}
