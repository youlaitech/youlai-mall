package com.fly4j.yshop.pms.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.common.core.controller.BaseController;
import com.fly4j.yshop.pms.pojo.entity.PmsSpec;
import com.fly4j.yshop.pms.service.IPmsSpecificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/specifications")
@Slf4j
public class PmsSpecificationController extends BaseController {

    @Resource
    private IPmsSpecificationService iPmsSpecificationService;

    @GetMapping("/pageNum/{pageNum}/pageSize/{pageSize}")
    public R<Page<PmsSpec>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, PmsSpec pmsSpec) {
        Page<PmsSpec> page = new Page<>(pageNum, pageSize);
        Page<PmsSpec> data = (Page<PmsSpec>) iPmsSpecificationService.page(page, new LambdaQueryWrapper<PmsSpec>()
                .eq(StrUtil.isNotBlank(pmsSpec.getName()), PmsSpec::getName, pmsSpec.getName())
                .orderByDesc(PmsSpec::getCreate_time));
        return R.ok(data);
    }

    @GetMapping()
    public R list() {
        List<PmsSpec> list = iPmsSpecificationService.list();
        return R.ok(list);
    }

    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        PmsSpec user = iPmsSpecificationService.getById(id);
        return R.ok(user);
    }

    @PostMapping
    public R add(@RequestBody PmsSpec PmsSpec) {
        boolean status = iPmsSpecificationService.save(PmsSpec);
        return status ? R.ok(null) : R.failed("新增失败");
    }

    @PutMapping(value = "/{id}")
    public R update(@PathVariable("id") Long id, @RequestBody PmsSpec PmsSpec) {
        boolean status = iPmsSpecificationService.updateById(PmsSpec);
        return status ? R.ok(null) : R.failed("更新失败");
    }

    @DeleteMapping("/{ids}")
    public R delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iPmsSpecificationService.removeByIds(ids);
        return status ? R.ok(null) : R.failed("删除失败");
    }

}
