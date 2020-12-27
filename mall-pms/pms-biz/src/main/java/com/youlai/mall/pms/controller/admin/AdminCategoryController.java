package com.youlai.mall.pms.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.youlai.common.core.enums.QueryModeEnum;
import com.youlai.common.core.result.Result;
import com.youlai.mall.pms.pojo.PmsCategory;
import com.youlai.mall.pms.pojo.vo.CategoryVO;
import com.youlai.mall.pms.service.IPmsCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "商品分类接口")
@RestController
@RequestMapping("/categories")
@Slf4j
@AllArgsConstructor
public class AdminCategoryController {

    private IPmsCategoryService iPmsCategoryService;

    @ApiOperation(value = "列表分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryMode", paramType = "query", dataType = "String"),
    })
    @GetMapping
    public Result list(String queryMode) {
        QueryModeEnum queryModeEnum = QueryModeEnum.getValue(queryMode);
        PmsCategory category = new PmsCategory();
        List list;
        switch (queryModeEnum) {
            case CASCADER:
                list = iPmsCategoryService.listForCascader(category);
                return Result.success(list);
            default:
                list = iPmsCategoryService.listForTree(category);
                return Result.success(list);
        }
    }

    @ApiOperation(value = "商品分类详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "商品分类id", required = true, paramType = "path", dataType = "Integer")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        PmsCategory category = iPmsCategoryService.getById(id);
        return Result.success(category);
    }

    @ApiOperation(value = "新增商品分类", httpMethod = "POST")
    @ApiImplicitParam(name = "category", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsCategory")
    @PostMapping
    public Result add(@RequestBody PmsCategory category) {
        iPmsCategoryService.save(category);
        CategoryVO categoryVO = new CategoryVO();
        BeanUtil.copyProperties(category, categoryVO);
        return Result.success(categoryVO);
    }

    @ApiOperation(value = "修改商品分类", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品分类id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "category", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsCategory")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Integer id,
            @RequestBody PmsCategory category) {
        iPmsCategoryService.updateById(category);
        return Result.success(category);
    }

    @ApiOperation(value = "删除商品分类", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "id集合,以英文逗号','分隔", required = true, paramType = "query", allowMultiple = true, dataType = "String")
    @DeleteMapping
    public Result delete(@RequestParam String ids) {
        iPmsCategoryService.removeByIds(Arrays.asList(ids.split(",")).stream().map(id -> Long.parseLong(id)).collect(Collectors.toList()));
        return Result.success();
    }

    @ApiOperation(value = "修改商品分类(部分更新)", httpMethod = "PATCH")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "category", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsCategory")
    })
    @PatchMapping(value = "/{id}")
    public Result patch(@PathVariable Integer id, @RequestBody PmsCategory category) {
        LambdaUpdateWrapper<PmsCategory> luw = new LambdaUpdateWrapper<PmsCategory>().eq(PmsCategory::getId, id);
        if (category.getStatus() != null) { // 状态更新
            luw.set(PmsCategory::getStatus, category.getStatus());
        }
        boolean update = iPmsCategoryService.update(luw);
        return Result.success(update);
    }
}
