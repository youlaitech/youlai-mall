package com.fly4j.yshop.pms.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.fly4j.common.core.controller.BaseController;
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

import java.util.Arrays;
import java.util.List;

@Api(tags = "分类接口")
@RestController
@RequestMapping("/categories")
@Slf4j
public class PmsCategoryController extends BaseController {

    @Autowired
    private IPmsCategoryService iPmsCategoryService;

    @ApiOperation(value = "分类列表", httpMethod = "GET")
    @GetMapping()
    public R list() {
        List<PmsCategory> list = iPmsCategoryService.list();
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
    public R update(@PathVariable("id") Long id, @RequestBody PmsCategory PmsCategory) {
        boolean status = iPmsCategoryService.updateById(PmsCategory);
        return status ? R.ok(null) : R.failed("更新失败");
    }

    @ApiOperation(value = "删除分类", httpMethod = "delete")
    @ApiImplicitParam(name = "ids", value = "分类id", required = true, paramType = "query", allowMultiple = true, dataType = "Long")
    @DeleteMapping()
    public R delete(@PathVariable Long[] ids) {
        boolean status = iPmsCategoryService.removeByIds(Arrays.asList(ids));
        return status ? R.ok(null) : R.failed("删除失败");
    }


    @ApiOperation(value = "分类级联列表", httpMethod = "GET")
    @GetMapping(value = "/cascade")
    public R cascade() {
        List<CascaderVO> cascadeList = iPmsCategoryService.cascadeList();
        return R.ok(cascadeList);
    }

}
