package com.fly4j.shop.marketing.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.shop.marketing.pojo.entity.SeckillSession;
import com.fly4j.shop.marketing.service.ISeckillSessionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/seckillSession")
public class SeckillSessionController {

    @Autowired
    private ISeckillSessionService iSeckillSessionService;

    @GetMapping("/pageNum/{pageNum}/pageSize/{pageSize}")
    public R<Page<SeckillSession>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, SeckillSession seckill) {
        Page<SeckillSession> page = new Page<>(pageNum, pageSize);
        Page<SeckillSession> data = (Page) iSeckillSessionService.page(page,
                new LambdaQueryWrapper<SeckillSession>()
                        .like(StringUtils.isNotBlank(seckill.getName()),
                                SeckillSession::getName,
                                seckill.getName())
                        .orderByAsc(SeckillSession::getName)
        );
        return R.ok(data);
    }


    @GetMapping("/{id}")
    public R get(@PathVariable("id") Integer id) {
        SeckillSession seckill = iSeckillSessionService.getById(id);
        return R.ok(seckill);
    }

    @PostMapping
    public R add(@RequestBody SeckillSession seckill) {
        boolean result = iSeckillSessionService.save(seckill);
        if (result) {
            return R.ok("新增成功");
        } else {
            return R.failed("新增失败");
        }
    }

    @PutMapping("/{id}")
    public R update(@PathVariable("id") Integer id, @RequestBody SeckillSession seckill) {
        boolean result = iSeckillSessionService.updateById(seckill);
        if (result) {
            return R.ok("更新成功");
        } else {
            return R.failed("更新失败");
        }
    }


    @DeleteMapping("/{ids}")
    public R delete(@PathVariable Long[] ids) {
        boolean status = iSeckillSessionService.removeByIds(Arrays.asList(ids));
        if (status) {
            return R.ok("删除成功");
        } else {
            return R.failed("删除失败");
        }
    }

    @PutMapping("/id/{id}/status/{status}")
    public R updateStatus(@PathVariable Integer id, @PathVariable Integer status) {
        boolean result = iSeckillSessionService.update(new LambdaUpdateWrapper<SeckillSession>()
                .eq(SeckillSession::getId, id)
                .set(SeckillSession::getStatus, status));
        if (result) {
            return R.ok("更新成功");
        } else {
            return R.failed("更新失败");
        }
    }

    @GetMapping("/list")
    public R list() {
        List<SeckillSession> list = iSeckillSessionService.list(new LambdaQueryWrapper<SeckillSession>()
                .orderByAsc(SeckillSession::getName));
        return R.ok(list);
    }
}
