package com.fly4j.yshop.pms.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.common.core.controller.BaseController;
import com.fly4j.yshop.pms.pojo.entity.PmsAttribute;
import com.fly4j.yshop.pms.service.IPmsAttributeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/attributes")
@Slf4j
public class PmsAttributeController extends BaseController {

    @Resource
    private IPmsAttributeService iPmsAttributeService;

    @GetMapping("/pageNum/{pageNum}/pageSize/{pageSize}")
    public R<Page<PmsAttribute>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, PmsAttribute pmsAttribute) {
        Page<PmsAttribute> page = new Page<>(pageNum, pageSize);
        Page<PmsAttribute> data = (Page<PmsAttribute>) iPmsAttributeService.page(page, new LambdaQueryWrapper<PmsAttribute>()
                .eq(StrUtil.isNotBlank(pmsAttribute.getName()), PmsAttribute::getName, pmsAttribute.getName())
                .orderByDesc(PmsAttribute::getCreate_time));
        return R.ok(data);
    }

    @GetMapping()
    public R list() {
        List<PmsAttribute> list = iPmsAttributeService.list();
        return R.ok(list);
    }

    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        PmsAttribute user = iPmsAttributeService.getById(id);
        return R.ok(user);
    }

    @PostMapping
    public R add(@RequestBody PmsAttribute pmsAttribute) {
        boolean status = iPmsAttributeService.save(pmsAttribute);
        return status ? R.ok(null) : R.failed("新增失败");
    }

    @PutMapping(value = "/{id}")
    public R update(@PathVariable("id") Long id, @RequestBody PmsAttribute pmsAttribute) {
        boolean status = iPmsAttributeService.updateById(pmsAttribute);
        return status ? R.ok(null) : R.failed("更新失败");
    }

    @DeleteMapping("/{ids}")
    public R delete(@PathVariable Long[] ids) {
        boolean status = iPmsAttributeService.removeByIds(Arrays.asList(ids));
        return status ? R.ok(null) : R.failed("删除失败");
    }

}
