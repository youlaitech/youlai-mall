package com.fly4j.shop.marketing.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.shop.marketing.pojo.entity.HomeAdvertisementEntity;
import com.fly4j.shop.marketing.service.IHomeAdvertisementService;
import com.fly4j.shop.marketing.service.ISpikeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/home/advertisement")
public class HomeAdvertisementController {

    @Autowired
    private IHomeAdvertisementService iHomeAdvertisementEntityService;

    @GetMapping("/pageNum/{pageNum}/pageSize/{pageSize}")
    public R<Page<HomeAdvertisementEntity>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, HomeAdvertisementEntity spikeEntity) {
        Page<HomeAdvertisementEntity> page = new Page<>(pageNum, pageSize);
        Page<HomeAdvertisementEntity> data = (Page) iHomeAdvertisementEntityService.page(page,
                new LambdaQueryWrapper<HomeAdvertisementEntity>()
                        .like(StringUtils.isNotBlank(spikeEntity.getName()),
                                HomeAdvertisementEntity::getName,
                                spikeEntity.getName())
                        .orderByDesc(HomeAdvertisementEntity::getCreateTime)
        );
        return R.ok(data);
    }


    @GetMapping("/{id}")
    public R get(@PathVariable("id") Integer id) {
        HomeAdvertisementEntity spikeEntity = iHomeAdvertisementEntityService.getById(id);
        return R.ok(spikeEntity);
    }

    @PostMapping
    public R add(@RequestBody HomeAdvertisementEntity spikeEntity) {
        boolean result = iHomeAdvertisementEntityService.save(spikeEntity);
        if (result) {
            return R.ok("新增成功");
        } else {
            return R.failed("新增失败");
        }
    }

    @PutMapping("/{id}")
    public R update(@PathVariable("id") Integer id, @RequestBody HomeAdvertisementEntity spikeEntity) {
        boolean result = iHomeAdvertisementEntityService.updateById(spikeEntity);
        if (result) {
            return R.ok("更新成功");
        } else {
            return R.failed("更新失败");
        }
    }

    @DeleteMapping()
    public R delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iHomeAdvertisementEntityService.removeByIds(ids);
        if (status) {
            return R.ok("删除成功");
        } else {
            return R.failed("删除失败");
        }
    }

    @PutMapping("/id/{id}/status/{status}")
    public R updateStatus(@PathVariable Integer id, @PathVariable Integer status) {
        boolean result = iHomeAdvertisementEntityService.update(new LambdaUpdateWrapper<HomeAdvertisementEntity>()
                .eq(HomeAdvertisementEntity::getId, id)
                .set(HomeAdvertisementEntity::getStatus, status));
        if (result) {
            return R.ok("更新成功");
        } else {
            return R.failed("更新失败");
        }
    }
}
