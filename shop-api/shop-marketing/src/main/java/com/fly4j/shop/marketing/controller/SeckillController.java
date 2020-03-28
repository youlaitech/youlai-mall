package com.fly4j.shop.marketing.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.common.core.domain.Result;
import com.fly4j.shop.marketing.pojo.entity.Seckill;
import com.fly4j.shop.marketing.service.ISeckillService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    private ISeckillService iSeckillService;

    @GetMapping("/pageNum/{pageNum}/pageSize/{pageSize}")
    public R<Page<Seckill>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, Seckill seckill) {
        Page<Seckill> page = new Page<>(pageNum, pageSize);
        Page<Seckill> data = (Page) iSeckillService.page(page,
                new LambdaQueryWrapper<Seckill>()
                        .like(StringUtils.isNotBlank(seckill.getTitle()),
                                Seckill::getTitle,
                                seckill.getTitle())
                        .orderByDesc(Seckill::getCreateTime)
        );
        return R.ok(data);
    }


    @GetMapping("/{id}")
    public R get(@PathVariable("id") Integer id) {
        Seckill seckill = iSeckillService.getById(id);
        return R.ok(seckill);
    }

    @PostMapping
    public R add(@RequestBody Seckill seckill) {
        boolean result = iSeckillService.save(seckill);
        if (result) {
            return R.ok("新增成功");
        } else {
            return R.failed("新增失败");
        }
    }

    @PutMapping("/{id}")
    public R update(@PathVariable("id") Integer id, @RequestBody Seckill seckill) {
        boolean result = iSeckillService.updateById(seckill);
        if (result) {
            return R.ok("更新成功");
        } else {
            return R.failed("更新失败");
        }
    }


    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable Long[] ids) {
        boolean status = iSeckillService.removeByIds(Arrays.asList(ids));
        return Result.status(status);
    }



    @PutMapping("/id/{id}/status/{status}")
    public R updateStatus(@PathVariable Integer id, @PathVariable Integer status) {
        boolean result = iSeckillService.update(new LambdaUpdateWrapper<Seckill>()
                .eq(Seckill::getId, id)
                .set(Seckill::getStatus, status));
        if (result) {
            return R.ok("更新成功");
        } else {
            return R.failed("更新失败");
        }
    }
}
