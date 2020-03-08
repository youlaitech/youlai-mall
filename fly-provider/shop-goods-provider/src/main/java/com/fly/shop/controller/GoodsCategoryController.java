package com.fly.shop.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly.common.core.domain.Result;
import com.fly.shop.pojo.dto.GoodsCategoryDTO;
import com.fly.shop.pojo.entity.GoodsCategory;
import com.fly.shop.pojo.vo.TreeSelectVO;
import com.fly.shop.services.IGoodsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class GoodsCategoryController {

    @Autowired
    private IGoodsCategoryService iGoodsCategoryService;

    @GetMapping("/pageNum/{pageNum}/pageSize/{pageSize}")
    public Result<Page<GoodsCategory>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, GoodsCategory goodsCategory) {
        Page<GoodsCategory> page = new Page<>(pageNum, pageSize);
        Page data = iGoodsCategoryService.selectPage(page, goodsCategory);
        return Result.success(data);
    }

    @GetMapping("/list")
    public Result<List<GoodsCategoryDTO>> list(GoodsCategory goodsCategory){
        List<GoodsCategoryDTO> categoryList = iGoodsCategoryService.selectList(goodsCategory);
        return  Result.success(categoryList);
    }


    @GetMapping("/{id}")
    public Result get(@PathVariable Integer id) {
        GoodsCategory category = iGoodsCategoryService.getById(id);
        return Result.success(category);
    }

    @PostMapping
    public Result add(@RequestBody GoodsCategory goodsCategory) {
        boolean status = iGoodsCategoryService.save(goodsCategory);
        return Result.status(status);
    }

    @PutMapping(value = "/{id}")
    public Result update(@PathVariable("id") Integer id, @RequestBody GoodsCategory goodsCategory) {
        boolean status = iGoodsCategoryService.updateById(goodsCategory);
        return Result.status(status);
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable Integer[] ids) {
        boolean status = iGoodsCategoryService.removeByIds(Arrays.asList(ids));
        return Result.status(status);
    }

    @GetMapping("/treeSelect")
    public Result treeSelect(GoodsCategory goodsCategory) {
        List<TreeSelectVO> list = iGoodsCategoryService.treeSelect(goodsCategory);
        return Result.success(list);
    }



}
