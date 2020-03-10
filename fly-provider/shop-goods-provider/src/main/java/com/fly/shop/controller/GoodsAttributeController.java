package com.fly.shop.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly.common.core.controller.BaseController;
import com.fly.common.core.domain.Result;
import com.fly.shop.pojo.entity.GoodsAttribute;
import com.fly.shop.services.IGoodsAttributeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/attributes")
@Slf4j
public class GoodsAttributeController extends BaseController {

    @Resource
    private IGoodsAttributeService iGoodsAttributeService;


    @GetMapping("/pageNum/{pageNum}/pageSize/{pageSize}")
    public Result<Page<GoodsAttribute>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, GoodsAttribute attribute) {
        Page<GoodsAttribute> page = new Page<>(pageNum, pageSize);
        Page<GoodsAttribute> data =  iGoodsAttributeService.selectPage(page,attribute);
        return Result.success(data);
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        GoodsAttribute user = iGoodsAttributeService.getById(id);
        return Result.success(user);
    }

    @PostMapping
    public Result add(@RequestBody GoodsAttribute GoodsAttribute) {
        boolean status = iGoodsAttributeService.save(GoodsAttribute);
        return Result.status(status);
    }

    @PutMapping(value = "/{id}")
    public Result update(@PathVariable("id") Long id, @RequestBody GoodsAttribute GoodsAttribute) {
        boolean status = iGoodsAttributeService.updateById(GoodsAttribute);
        return Result.status(status);
    }

    @GetMapping("/list")
    public Result list() {
        List<GoodsAttribute> list = iGoodsAttributeService.list();
        return Result.success(list);
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable Long[] ids) {
        boolean status = iGoodsAttributeService.removeByIds(Arrays.asList(ids));
        return Result.status(status);
    }

}
