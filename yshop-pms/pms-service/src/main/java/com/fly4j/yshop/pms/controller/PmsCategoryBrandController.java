package com.fly4j.yshop.pms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.common.core.controller.BaseController;
import com.fly4j.yshop.pms.pojo.entity.PmsCategoryBrand;
import com.fly4j.yshop.pms.service.IPmsCategoryBrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/categoryBrand")
@Slf4j
public class PmsCategoryBrandController extends BaseController {

    @Resource
    private IPmsCategoryBrandService iPmsCategoryBrandService;

    @GetMapping("/pageNum/{pageNum}/pageSize/{pageSize}")
    public R<Page<PmsCategoryBrand>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, PmsCategoryBrand PmsCategoryBrand) {
        Page<PmsCategoryBrand> page = new Page<>(pageNum, pageSize);
        Page<PmsCategoryBrand> data = (Page<PmsCategoryBrand>) iPmsCategoryBrandService.page(page, new LambdaQueryWrapper<PmsCategoryBrand>());
        return R.ok(data);
    }

    @GetMapping()
    public R list() {
        List<PmsCategoryBrand> list = iPmsCategoryBrandService.list();
        return R.ok(list);
    }

    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        PmsCategoryBrand user = iPmsCategoryBrandService.getById(id);
        return R.ok(user);
    }

    @PostMapping
    public R add(@RequestBody PmsCategoryBrand PmsCategoryBrand) {
        boolean status = iPmsCategoryBrandService.save(PmsCategoryBrand);
        return status ? R.ok(null) : R.failed("新增失败");
    }

    @PutMapping(value = "/{id}")
    public R update(@PathVariable("id") Long id, @RequestBody PmsCategoryBrand PmsCategoryBrand) {
        boolean status = iPmsCategoryBrandService.updateById(PmsCategoryBrand);
        return status ? R.ok(null) : R.failed("更新失败");
    }

    @DeleteMapping("/{ids}")
    public R delete(@PathVariable Long[] ids) {
        boolean status = iPmsCategoryBrandService.removeByIds(Arrays.asList(ids));
        return status ? R.ok(null) : R.failed("删除失败");
    }

}
