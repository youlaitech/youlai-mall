package com.fly4j.shop.goods.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.common.core.domain.Result;
import com.fly4j.shop.goods.pojo.dto.GoodsDTO;
import com.fly4j.shop.goods.pojo.dto.SpikeGoodsDTO;
import com.fly4j.shop.goods.pojo.entity.Goods;
import com.fly4j.shop.goods.service.IGoodsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

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
        Page<Goods> data = (Page) iGoodsService.page(page, new LambdaQueryWrapper<Goods>()
                .like(StringUtils.isNotBlank(goods.getName()), Goods::getName, goods.getName())
                .eq(StringUtils.isNotBlank(goods.getGoodsSn()),Goods::getGoodsSn,goods.getGoodsSn())
//                .eq(Goods::getGoodsCategoryId,goods.getGoodsCategoryId())
//                .eq(Goods::getBrandId,goods.getBrandId())
//                .eq(Goods::getPublishStatus,goods.getPublishStatus())
//                .eq(Goods::getVerifyStatus,goods.getVerifyStatus())
        );
        return Result.success(data);
    }

    /**
     * 商品添加
     * @param goodsDto
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody GoodsDTO goodsDto) {
        boolean status = iGoodsService.add(goodsDto);
        return Result.status(status);
    }

    /**
     * 批量上下架
     * @param ids
     * @param publishStatus
     * @return
     */
    @PutMapping("/update/publishStatus")
    public Result updatePublishStatus(@RequestParam("ids") List<Long> ids,
                         @RequestParam("publishStatus") Integer publishStatus) {
        boolean status = iGoodsService.updatePublishStatus(ids, publishStatus);
        return Result.status(status);
    }

    /**
     * 批量设为新品
     * @param ids
     * @param newStatus
     * @return
     */
    @PutMapping("/update/newStatus")
    public Result updateNewStatus(@RequestParam("ids") List<Long> ids,
                         @RequestParam("newStatus") Integer newStatus) {
        boolean status = iGoodsService.updateNewStatus(ids, newStatus);
        return Result.status(status);
    }

    /**
     * 批量推荐商品
     * @param ids
     * @param recommendStatus
     * @return
     */
    @PutMapping("/update/recommendStatus")
    public Result updateRecommendStatus(@RequestParam("ids") List<Long> ids,
                         @RequestParam("recommendStatus") Integer recommendStatus) {
        boolean status = iGoodsService.updateRecommendStatus(ids, recommendStatus);
        return Result.status(status);
    }

    /**
     * 移入回收站
     * @param ids
     * @param deleteStatus
     * @return
     */
    @PutMapping("/update/deleteStatus")
    public Result updateDeleteStatus(@RequestParam("ids") List<Long> ids,
                         @RequestParam("deleteStatus") Integer deleteStatus) {
        boolean status = iGoodsService.updateDeleteStatus(ids, deleteStatus);
        return Result.status(status);
    }

    /**
     * 删除商品
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable Long[] ids) {
        boolean status = iGoodsService.removeByIds(Arrays.asList(ids));
        return Result.status(status);
    }

    /**
     * 根据商品id获取商品编辑信息
     * @param id
     * @return
     */
    @GetMapping("/updateInfo/{id}")
    public Result getUpdateInfo(@PathVariable Long id) {
        Goods goods = iGoodsService.getById(id);
        return Result.success(goods);
    }

    /**
     * 更新商品
     * @param id
     * @param goodsDto
     * @return
     */
    @PutMapping(value = "/update/{id}")
    public Result update(@PathVariable("id") Long id, @RequestBody GoodsDTO goodsDto) {
        boolean status = iGoodsService.update(id,goodsDto);
        return Result.status(status);
    }
}
