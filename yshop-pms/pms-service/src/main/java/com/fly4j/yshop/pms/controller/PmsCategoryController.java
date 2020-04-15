package com.fly4j.yshop.pms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.fly4j.common.constant.Constants;
import com.fly4j.common.core.controller.BaseController;
import com.fly4j.yshop.pms.pojo.dto.PmsCategoryDTO;
import com.fly4j.yshop.pms.pojo.entity.PmsCategory;
import com.fly4j.yshop.pms.pojo.vo.CascaderVO;
import com.fly4j.yshop.pms.service.IPmsCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@Api(tags = "分类接口")
@RestController
@RequestMapping("/categories")
@Slf4j
public class PmsCategoryController extends BaseController {

    @Autowired
    private IPmsCategoryService iPmsCategoryService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "分类名称", paramType = "query", dataType = "PmsCategory")
    })
    @ApiOperation(value = "分类列表", httpMethod = "GET")
    @GetMapping()
    public R list(@RequestParam(required = false) String name) {
        List<PmsCategoryDTO> list = iPmsCategoryService.selectList(name);
        return R.ok(list);
    }

    @ApiOperation(value = "新增分类", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pmsCategory", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsCategory")
    })
    @PostMapping
    public R save(@RequestBody PmsCategory pmsCategory) {
        boolean status = iPmsCategoryService.save(pmsCategory);
        return status ? R.ok(null) : R.failed("新增失败");
    }


    @ApiOperation(value = "分类详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "分类id", required = true, paramType = "path", dataType = "Long"),
    })
    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        PmsCategory user = iPmsCategoryService.getById(id);
        return R.ok(user);
    }


    @ApiOperation(value = "修改分类", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "pmsCategory", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsCategory")
    })
    @PutMapping(value = "/{id}")
    public R update(@PathVariable("id") Long id, @RequestBody PmsCategory pmsCategory) {
        boolean status = iPmsCategoryService.updateById(pmsCategory);
        return status ? R.ok(null) : R.failed("更新失败");
    }

    @ApiOperation(value = "删除分类", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "分类id", required = true, paramType = "query", allowMultiple = true, dataType = "Long")
    @DeleteMapping()
    public R delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iPmsCategoryService.removeByIds(ids);
        return status ? R.ok(null) : R.failed("删除失败");
    }


    @ApiOperation(value = "分类列表(级联)", httpMethod = "GET")
    @GetMapping(value = "/cascade")
    public R cascade() {
        List<CascaderVO> cascadeList = iPmsCategoryService.cascadeList();
        return R.ok(cascadeList);
    }

    @ApiOperation(value = "分类列表(一级)", httpMethod = "GET")
    @GetMapping(value = "/firstLevel")
    public R l1() {
        List<PmsCategory> list = new LinkedList<>();
        list.add(new PmsCategory().setId(0L).setName("顶级分类"));
        list.addAll(iPmsCategoryService.list(new LambdaQueryWrapper<PmsCategory>()
                .eq(PmsCategory::getLevel, Constants.CATEGORY_LEVEL1)));
        return R.ok(list);
    }

    @ApiOperation(value = "分类可见状态更新", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "分类id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "is_show", value = "显示状态", required = true, paramType = "path", dataType = "Integer")
    })
    @PutMapping("/id/{id}/is_show/{is_show}")
    public R updateIsShowStatus(@PathVariable Integer id, @PathVariable Integer is_show) {
        boolean result = iPmsCategoryService.update(new LambdaUpdateWrapper<PmsCategory>()
                .eq(PmsCategory::getId, id)
                .set(PmsCategory::getIs_show, is_show));
        if (result) {
            return R.ok("更新成功");
        } else {
            return R.failed("更新失败");
        }
    }

}
