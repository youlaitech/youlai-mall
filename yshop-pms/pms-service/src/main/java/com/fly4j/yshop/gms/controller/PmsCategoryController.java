package com.fly4j.yshop.gms.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.common.core.controller.BaseController;
import com.fly4j.yshop.gms.pojo.entity.PmsCategory;
import com.fly4j.yshop.gms.pojo.entity.PmsCategory;
import com.fly4j.yshop.gms.service.IPmsCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/gms/categories")
@Slf4j
public class PmsCategoryController extends BaseController {

    @Resource
    private IPmsCategoryService iPmsCategoryService;

    @GetMapping("/pageNum/{pageNum}/pageSize/{pageSize}")
    public R<Page<PmsCategory>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, PmsCategory attribute) {
        Page<PmsCategory> page = new Page<>(pageNum, pageSize);
        Page<PmsCategory> data = (Page<PmsCategory>) iPmsCategoryService.page(page, new LambdaQueryWrapper<PmsCategory>()
                .orderByDesc(PmsCategory::getCreateTime));
        return R.ok(data);
    }

    @GetMapping()
    public R list() {
        List<PmsCategory> list = iPmsCategoryService.list();
        return R.ok(list);
    }

    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        PmsCategory user = iPmsCategoryService.getById(id);
        return R.ok(user);
    }

    @PostMapping
    public R add(@RequestBody PmsCategory PmsCategory) {
        boolean status = iPmsCategoryService.save(PmsCategory);
        return status ? R.ok(null) : R.failed("新增失败");
    }

    @PutMapping(value = "/{id}")
    public R update(@PathVariable("id") Long id, @RequestBody PmsCategory PmsCategory) {
        boolean status = iPmsCategoryService.updateById(PmsCategory);
        return status ? R.ok(null) : R.failed("更新失败");
    }

    @DeleteMapping("/{ids}")
    public R delete(@PathVariable Long[] ids) {
        boolean status = iPmsCategoryService.removeByIds(Arrays.asList(ids));
        return status ? R.ok(null) : R.failed("删除失败");
    }

}
