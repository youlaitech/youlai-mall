package com.fly4j.shop.marketing.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.shop.marketing.pojo.entity.SpikeRecordEntity;
import com.fly4j.shop.marketing.service.ISpikeRecordService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/spikeRecord")
public class SpikeRecordController {

    @Autowired
    private ISpikeRecordService iSpikeRecordService;

    @GetMapping("/pageNum/{pageNum}/pageSize/{pageSize}")
    public R<Page<SpikeRecordEntity>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, SpikeRecordEntity spikeRecordEntity) {
        Page<SpikeRecordEntity> page = new Page<>(pageNum, pageSize);
        Page<SpikeRecordEntity> data = (Page) iSpikeRecordService.page(page,
                new LambdaQueryWrapper<SpikeRecordEntity>()
                        .like(StringUtils.isNotBlank(spikeRecordEntity.getGoodsName()),
                                SpikeRecordEntity::getGoodsName,
                                spikeRecordEntity.getGoodsName())
                        .orderByDesc(SpikeRecordEntity::getCreateTime)
        );
        return R.ok(data);
    }


    @GetMapping("/{id}")
    public R get(@PathVariable("id") Integer id) {
        SpikeRecordEntity spikeRecordEntity = iSpikeRecordService.getById(id);
        return R.ok(spikeRecordEntity);
    }

    @PostMapping
    public R add(@RequestBody SpikeRecordEntity spikeRecordEntity) {
        boolean result = iSpikeRecordService.save(spikeRecordEntity);
        if (result) {
            return R.ok("新增成功");
        } else {
            return R.failed("新增失败");
        }
    }

    @PutMapping("/{id}")
    public R update(@PathVariable("id") Integer id, @RequestBody SpikeRecordEntity spikeRecordEntity) {
        boolean result = iSpikeRecordService.updateById(spikeRecordEntity);
        if (result) {
            return R.ok("更新成功");
        } else {
            return R.failed("更新失败");
        }
    }


    @DeleteMapping("/{ids}")
    public R delete(@PathVariable Long[] ids) {
        boolean status = iSpikeRecordService.removeByIds(Arrays.asList(ids));
        if (status) {
            return R.ok("删除成功");
        } else {
            return R.failed("删除失败");
        }
    }

}
