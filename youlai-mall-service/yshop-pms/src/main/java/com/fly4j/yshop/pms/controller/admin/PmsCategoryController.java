package com.fly4j.yshop.pms.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youlai.common.api.Result;
import com.youlai.common.core.controller.BaseController;
import com.fly4j.yshop.pms.pojo.dto.admin.PmsCategoryDTO;
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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Api(tags = "ADMIN-商品分类")
@RestController
@RequestMapping("/categories")
@Slf4j
public class PmsCategoryController extends BaseController {

    @Autowired
    private IPmsCategoryService iPmsCategoryService;

    @ApiOperation(value = "分类列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "分类名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "is_cascade", value = "是否级联", paramType = "query", dataType = "Boolean"),
            @ApiImplicitParam(name = "level", value = "分类级别", paramType = "query", dataType = "Integer")
    })
    @GetMapping
    public Result list( String name, Boolean is_cascade,Integer level) {
        if (is_cascade) {
            // 级联下拉列表
            List<CascaderVO> cascadeList = iPmsCategoryService.cascadeList();
            return Result.ok(cascadeList);
        } else if (level == 1) {
            // 一级下拉列表
            List<PmsCategory> list = new LinkedList<>();
            list.add(new PmsCategory().setId(0L).setName("顶级分类"));
            List<PmsCategory> result = iPmsCategoryService.list(new LambdaQueryWrapper<PmsCategory>()
                    .eq(PmsCategory::getLevel, 1));
            list.addAll(result);
            return Result.ok(list);
        }else{
            // 树形列表
            Map<String,Object> paramMap=new HashMap<>();
            paramMap.put("name",name);
            List<PmsCategoryDTO> list = iPmsCategoryService.treeList(paramMap);
            return Result.ok(list);
        }
    }

    @ApiOperation(value = "新增分类", httpMethod = "POST")
    @ApiImplicitParam(name = "category", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsCategory")
    @PostMapping
    public Result save(@RequestBody PmsCategory category) {
        boolean status = iPmsCategoryService.save(category);
        return Result.status(status);
    }

    @ApiOperation(value = "分类详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", required = true, paramType = "path", dataType = "Integer")
    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        PmsCategory category = iPmsCategoryService.getById(id);
        return Result.ok(category);
    }

    @ApiOperation(value = "修改分类", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "category", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsCategory")
    })
    @PutMapping(value = "/{id}")
    public Result update(@PathVariable("id") Long id,
                         @RequestBody PmsCategory category
    ) {
        boolean status = iPmsCategoryService.updateById(category);
        return Result.status(status);
    }

    @ApiOperation(value = "删除分类", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "分类id", required = true, paramType = "query", allowMultiple = true, dataType = "Integer")
    @DeleteMapping()
    public Result delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iPmsCategoryService.removeByIds(ids);
        return Result.status(status);
    }
}
