package com.fly.shop.controller;

import com.fly.shop.domain.PmsBrand;
import com.fly.shop.feign.IPmsBrandService;
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
public class PmsBrandController {
    @Resource
    private IPmsBrandService iPmsBrandService;

    @GetMapping("/list")
    public List<PmsBrand> getList() {
        List<PmsBrand> list = iPmsBrandService.getAll();
        return list;
    }
}
