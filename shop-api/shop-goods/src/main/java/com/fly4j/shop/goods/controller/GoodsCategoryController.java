package com.fly4j.shop.goods.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.common.core.domain.Result;
import com.fly4j.shop.goods.pojo.dto.GoodsCategoryDTO;
import com.fly4j.shop.goods.service.IGoodsCategoryAttributeRelationService;
import com.fly4j.shop.goods.service.IGoodsCategoryService;
import com.fly4j.shop.goods.pojo.entity.GoodsCategory;
import com.fly4j.shop.goods.pojo.entity.GoodsCategoryAttributeRelation;
import com.fly4j.shop.goods.pojo.vo.TreeSelectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class GoodsCategoryController {

    @Autowired
    private IGoodsCategoryService iGoodsCategoryService;

    @Autowired
    private IGoodsCategoryAttributeRelationService iGoodsCategoryAttributeRelationService;

    @GetMapping("/pageNum/{pageNum}/pageSize/{pageSize}")
    public Result<Page<GoodsCategory>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, GoodsCategory goodsCategory) {
        Page<GoodsCategory> page = new Page<>(pageNum, pageSize);
        Page data = iGoodsCategoryService.selectPage(page, goodsCategory);
        return Result.success(data);
    }

    @GetMapping("/list")
    public Result<List<GoodsCategoryDTO>> list(GoodsCategory goodsCategory) {
        List<GoodsCategoryDTO> categoryList = iGoodsCategoryService.selectList(goodsCategory);
        return Result.success(categoryList);
    }


    @GetMapping("/{id}")
    public Result get(@PathVariable Integer id) {
        GoodsCategory category = iGoodsCategoryService.getById(id);
        GoodsCategoryDTO categoryDTO = new GoodsCategoryDTO();
        if (category != null) {
            BeanUtil.copyProperties(category, categoryDTO);
            Set<Integer> attributeIds = iGoodsCategoryAttributeRelationService.list(
                    new LambdaQueryWrapper<GoodsCategoryAttributeRelation>().eq(
                            GoodsCategoryAttributeRelation::getCategoryId, category.getCategoryId()
                    )).stream().map(item -> item.getAttributeId()).collect(Collectors.toSet());
            categoryDTO.setGoodsAttributeIds(attributeIds);
        }
        return Result.success(categoryDTO);
    }

    @PostMapping
    public Result add(@RequestBody GoodsCategoryDTO goodsCategoryDTO) {
        boolean status = iGoodsCategoryService.add(goodsCategoryDTO);
        return Result.status(status);
    }

    @PutMapping(value = "/{id}")
    public Result update(@PathVariable("id") Integer id, @RequestBody GoodsCategoryDTO goodsCategoryDTO) {
        boolean status = iGoodsCategoryService.update(goodsCategoryDTO);
        return Result.status(status);
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable Integer[] ids) {
        List<Integer> idList = Arrays.asList(ids);
        boolean status = iGoodsCategoryService.removeByIds(idList);
        if (status) {
            idList.forEach(categoryId -> {
                iGoodsCategoryAttributeRelationService.remove(new LambdaQueryWrapper<GoodsCategoryAttributeRelation>()
                        .eq(GoodsCategoryAttributeRelation::getCategoryId, categoryId)
                );
            });
        }
        return Result.status(status);
    }

    @GetMapping("/treeSelect")
    public Result treeSelect(GoodsCategory goodsCategory) {
        List<TreeSelectVO> list = iGoodsCategoryService.treeSelect(goodsCategory);
        return Result.success(list);
    }

    @PutMapping("/id/{id}/isNav/{isNav}")
    public Result updateNavStatus(@PathVariable Integer id , @PathVariable Integer isNav){
        boolean status = iGoodsCategoryService.update(new LambdaUpdateWrapper<GoodsCategory>()
                .eq(GoodsCategory::getCategoryId, id)
                .set(GoodsCategory::getIsNav, isNav));
        return Result.success(status);
    }

    @PutMapping("/id/{id}/isShow/{isShow}")
    public Result updateShowStatus(@PathVariable Integer id , @PathVariable Integer isShow){
        boolean status = iGoodsCategoryService.update(new LambdaUpdateWrapper<GoodsCategory>()
                .eq(GoodsCategory::getCategoryId, id)
                .set(GoodsCategory::getIsShow, isShow));
        return Result.success(status);
    }
}
