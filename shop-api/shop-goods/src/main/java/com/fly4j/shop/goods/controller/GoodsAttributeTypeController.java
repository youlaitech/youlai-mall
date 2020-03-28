package com.fly4j.shop.goods.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.common.core.controller.BaseController;
import com.fly4j.common.core.domain.Result;
import com.fly4j.shop.goods.service.IGoodsAttributeTypeService;
import com.fly4j.shop.goods.pojo.entity.GoodsAttributeType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/attributes/type")
@Slf4j
public class GoodsAttributeTypeController extends BaseController {

    @Resource
    private IGoodsAttributeTypeService iGoodsAttributeTypeService;

    @GetMapping("/pageNum/{pageNum}/pageSize/{pageSize}")
    public Result<Page<GoodsAttributeType>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, GoodsAttributeType attributeType) {
        Page<GoodsAttributeType> page = new Page<>(pageNum, pageSize);
        Page<GoodsAttributeType> data = (Page) iGoodsAttributeTypeService.page(page,
                new LambdaQueryWrapper<GoodsAttributeType>()
                        .like(StringUtils.isNotBlank(attributeType.getAttributeTypeName()),
                                GoodsAttributeType::getAttributeTypeName, attributeType.getAttributeTypeName())
                .orderByAsc(GoodsAttributeType::getAttributeTypeId)
        );
        return Result.success(data);
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        GoodsAttributeType user = iGoodsAttributeTypeService.getById(id);
        return Result.success(user);
    }

    @PostMapping
    public Result add(@RequestBody GoodsAttributeType GoodsAttributeType) {
        boolean status = iGoodsAttributeTypeService.save(GoodsAttributeType);
        return Result.status(status);
    }

    @PutMapping(value = "/{id}")
    public Result update(@PathVariable("id") Long id, @RequestBody GoodsAttributeType GoodsAttributeType) {
        boolean status = iGoodsAttributeTypeService.updateById(GoodsAttributeType);
        return Result.status(status);
    }

    @GetMapping("/list")
    public Result list() {
        List<GoodsAttributeType> list = iGoodsAttributeTypeService.list();
        return Result.success(list);
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable Long[] ids) {
        boolean status = iGoodsAttributeTypeService.removeByIds(Arrays.asList(ids));
        return Result.status(status);
    }

}
