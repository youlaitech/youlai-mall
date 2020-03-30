package com.fly4j.shop.marketing.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.shop.marketing.pojo.entity.SeckillRecord;
import com.fly4j.shop.marketing.service.ISeckillRecordService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/seckillRecord")
public class SeckillRecordController {

    @Autowired
    private ISeckillRecordService iSeckillRecordService;

    @GetMapping("/pageNum/{pageNum}/pageSize/{pageSize}")
    public R<Page<SeckillRecord>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, SeckillRecord seckillRecord) {
        Page<SeckillRecord> page = new Page<>(pageNum, pageSize);
        Page<SeckillRecord> data = (Page) iSeckillRecordService.page(page,
                new LambdaQueryWrapper<SeckillRecord>()
                        .like(StringUtils.isNotBlank(seckillRecord.getGoodsName()),
                                SeckillRecord::getGoodsName,
                                seckillRecord.getGoodsName())
                        .orderByDesc(SeckillRecord::getCreateTime)
        );
        return R.ok(data);
    }


    @GetMapping("/{id}")
    public R get(@PathVariable("id") Integer id) {
        SeckillRecord seckillRecord = iSeckillRecordService.getById(id);
        return R.ok(seckillRecord);
    }

    @PostMapping
    public R add(@RequestBody SeckillRecord seckillRecord) {
        boolean result = iSeckillRecordService.save(seckillRecord);
        if (result) {
            return R.ok("新增成功");
        } else {
            return R.failed("新增失败");
        }
    }

    @PutMapping("/{id}")
    public R update(@PathVariable("id") Integer id, @RequestBody SeckillRecord seckillRecord) {
        boolean result = iSeckillRecordService.updateById(seckillRecord);
        if (result) {
            return R.ok("更新成功");
        } else {
            return R.failed("更新失败");
        }
    }


    @DeleteMapping("/{ids}")
    public R delete(@PathVariable Long[] ids) {
        boolean status = iSeckillRecordService.removeByIds(Arrays.asList(ids));
        if (status) {
            return R.ok("删除成功");
        } else {
            return R.failed("删除失败");
        }
    }

}
