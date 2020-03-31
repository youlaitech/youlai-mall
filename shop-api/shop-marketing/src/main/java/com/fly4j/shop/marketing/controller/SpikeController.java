package com.fly4j.shop.marketing.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.shop.marketing.pojo.entity.SpikeEntity;
import com.fly4j.shop.marketing.service.ISpikeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


@RestController
@RequestMapping("/spike")
public class SpikeController {

    @Autowired
    private ISpikeService iSpikeService;

    @GetMapping("/pageNum/{pageNum}/pageSize/{pageSize}")
    public R<Page<SpikeEntity>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, SpikeEntity spikeEntity) {
        Page<SpikeEntity> page = new Page<>(pageNum, pageSize);
        Page<SpikeEntity> data = (Page) iSpikeService.page(page,
                new LambdaQueryWrapper<SpikeEntity>()
                        .like(StringUtils.isNotBlank(spikeEntity.getTitle()),
                                SpikeEntity::getTitle,
                                spikeEntity.getTitle())
                        .orderByDesc(SpikeEntity::getCreateTime)
        );
        return R.ok(data);
    }


    @GetMapping("/{id}")
    public R get(@PathVariable("id") Integer id) {
        SpikeEntity spikeEntity = iSpikeService.getById(id);
        return R.ok(spikeEntity);
    }

    @PostMapping
    public R add(@RequestBody SpikeEntity spikeEntity) {
        boolean result = iSpikeService.save(spikeEntity);
        if (result) {
            return R.ok("新增成功");
        } else {
            return R.failed("新增失败");
        }
    }

    @PutMapping("/{id}")
    public R update(@PathVariable("id") Integer id, @RequestBody SpikeEntity spikeEntity) {
        boolean result = iSpikeService.updateById(spikeEntity);
        if (result) {
            return R.ok("更新成功");
        } else {
            return R.failed("更新失败");
        }
    }


    @DeleteMapping("/{ids}")
    public R delete(@PathVariable Long[] ids) {
        boolean status = iSpikeService.removeByIds(Arrays.asList(ids));
        if (status) {
            return R.ok("删除成功");
        } else {
            return R.failed("删除失败");
        }
    }



    @PutMapping("/id/{id}/status/{status}")
    public R updateStatus(@PathVariable Integer id, @PathVariable Integer status) {
        boolean result = iSpikeService.update(new LambdaUpdateWrapper<SpikeEntity>()
                .eq(SpikeEntity::getId, id)
                .set(SpikeEntity::getStatus, status));
        if (result) {
            return R.ok("更新成功");
        } else {
            return R.failed("更新失败");
        }
    }
}
