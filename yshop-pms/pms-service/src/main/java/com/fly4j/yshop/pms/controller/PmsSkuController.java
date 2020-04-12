package com.fly4j.yshop.pms.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.common.core.controller.BaseController;
import com.fly4j.yshop.pms.pojo.entity.PmsSku;
import com.fly4j.yshop.pms.service.IPmsSkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/sku")
@Slf4j
public class PmsSkuController extends BaseController {

    @Resource
    private IPmsSkuService iPmsSkuService;

    @GetMapping("/pageNum/{pageNum}/pageSize/{pageSize}")
    public R<Page<PmsSku>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, PmsSku pmsSku) {
        Page<PmsSku> page = new Page<>(pageNum, pageSize);
        Page<PmsSku> data = (Page<PmsSku>) iPmsSkuService.page(page, new LambdaQueryWrapper<PmsSku>()
                .eq(StrUtil.isNotBlank(pmsSku.getSpecs()), PmsSku::getSpecs, pmsSku.getSpecs())
                .orderByDesc(PmsSku::getCreate_time));
        return R.ok(data);
    }

    @GetMapping()
    public R list() {
        List<PmsSku> list = iPmsSkuService.list();
        return R.ok(list);
    }

    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        PmsSku user = iPmsSkuService.getById(id);
        return R.ok(user);
    }

    @PostMapping
    public R add(@RequestBody PmsSku PmsSku) {
        boolean status = iPmsSkuService.save(PmsSku);
        return status ? R.ok(null) : R.failed("新增失败");
    }

    @PutMapping(value = "/{id}")
    public R update(@PathVariable("id") Long id, @RequestBody PmsSku PmsSku) {
        boolean status = iPmsSkuService.updateById(PmsSku);
        return status ? R.ok(null) : R.failed("更新失败");
    }

    @DeleteMapping("/{ids}")
    public R delete(@PathVariable Long[] ids) {
        boolean status = iPmsSkuService.removeByIds(Arrays.asList(ids));
        return status ? R.ok(null) : R.failed("删除失败");
    }

}
