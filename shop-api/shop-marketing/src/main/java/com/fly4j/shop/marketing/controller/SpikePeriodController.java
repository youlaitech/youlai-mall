package com.fly4j.shop.marketing.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.shop.marketing.pojo.entity.SpikePeriodEntity;
import com.fly4j.shop.marketing.service.ISpikePeriodService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/spikePeriod")
public class SpikePeriodController {

    @Autowired
    private ISpikePeriodService iSpikePeriodService;

    @GetMapping("/pageNum/{pageNum}/pageSize/{pageSize}")
    public R<Page<SpikePeriodEntity>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, SpikePeriodEntity seckill) {
        Page<SpikePeriodEntity> page = new Page<>(pageNum, pageSize);
        Page<SpikePeriodEntity> data = (Page) iSpikePeriodService.page(page,
                new LambdaQueryWrapper<SpikePeriodEntity>()
                        .like(StringUtils.isNotBlank(seckill.getName()),
                                SpikePeriodEntity::getName,
                                seckill.getName())
                        .orderByAsc(SpikePeriodEntity::getName)
        );
        return R.ok(data);
    }


    @GetMapping("/{id}")
    public R get(@PathVariable("id") Integer id) {
        SpikePeriodEntity seckill = iSpikePeriodService.getById(id);
        return R.ok(seckill);
    }

    @PostMapping
    public R add(@RequestBody SpikePeriodEntity seckill) {
        boolean result = iSpikePeriodService.save(seckill);
        if (result) {
            return R.ok("新增成功");
        } else {
            return R.failed("新增失败");
        }
    }

    @PutMapping("/{id}")
    public R update(@PathVariable("id") Integer id, @RequestBody SpikePeriodEntity seckill) {
        boolean result = iSpikePeriodService.updateById(seckill);
        if (result) {
            return R.ok("更新成功");
        } else {
            return R.failed("更新失败");
        }
    }


    @DeleteMapping("/{ids}")
    public R delete(@PathVariable Long[] ids) {
        boolean status = iSpikePeriodService.removeByIds(Arrays.asList(ids));
        if (status) {
            return R.ok("删除成功");
        } else {
            return R.failed("删除失败");
        }
    }

    @PutMapping("/id/{id}/status/{status}")
    public R updateStatus(@PathVariable Integer id, @PathVariable Integer status) {
        boolean result = iSpikePeriodService.update(new LambdaUpdateWrapper<SpikePeriodEntity>()
                .eq(SpikePeriodEntity::getId, id)
                .set(SpikePeriodEntity::getStatus, status));
        if (result) {
            return R.ok("更新成功");
        } else {
            return R.failed("更新失败");
        }
    }

    @GetMapping("/list")
    public R list() {
        List<SpikePeriodEntity> list = iSpikePeriodService.list(new LambdaQueryWrapper<SpikePeriodEntity>()
                .orderByAsc(SpikePeriodEntity::getName));
        return R.ok(list);
    }
}
