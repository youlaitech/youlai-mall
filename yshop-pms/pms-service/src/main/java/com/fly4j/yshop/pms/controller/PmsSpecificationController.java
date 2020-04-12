package com.fly4j.yshop.pms.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.common.core.controller.BaseController;
import com.fly4j.yshop.pms.pojo.entity.PmsSpecification;
import com.fly4j.yshop.pms.service.IPmsSpecificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/specifications")
@Slf4j
public class PmsSpecificationController extends BaseController {

    @Resource
    private IPmsSpecificationService iPmsSpecificationService;

    @GetMapping("/pageNum/{pageNum}/pageSize/{pageSize}")
    public R<Page<PmsSpecification>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, PmsSpecification pmsSpecification) {
        Page<PmsSpecification> page = new Page<>(pageNum, pageSize);
        Page<PmsSpecification> data = (Page<PmsSpecification>) iPmsSpecificationService.page(page, new LambdaQueryWrapper<PmsSpecification>()
                .eq(StrUtil.isNotBlank(pmsSpecification.getName()), PmsSpecification::getName, pmsSpecification.getName())
                .orderByDesc(PmsSpecification::getCreate_time));
        return R.ok(data);
    }

    @GetMapping()
    public R list() {
        List<PmsSpecification> list = iPmsSpecificationService.list();
        return R.ok(list);
    }

    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        PmsSpecification user = iPmsSpecificationService.getById(id);
        return R.ok(user);
    }

    @PostMapping
    public R add(@RequestBody PmsSpecification PmsSpecification) {
        boolean status = iPmsSpecificationService.save(PmsSpecification);
        return status ? R.ok(null) : R.failed("新增失败");
    }

    @PutMapping(value = "/{id}")
    public R update(@PathVariable("id") Long id, @RequestBody PmsSpecification PmsSpecification) {
        boolean status = iPmsSpecificationService.updateById(PmsSpecification);
        return status ? R.ok(null) : R.failed("更新失败");
    }

    @DeleteMapping("/{ids}")
    public R delete(@PathVariable Long[] ids) {
        boolean status = iPmsSpecificationService.removeByIds(Arrays.asList(ids));
        return status ? R.ok(null) : R.failed("删除失败");
    }

}
