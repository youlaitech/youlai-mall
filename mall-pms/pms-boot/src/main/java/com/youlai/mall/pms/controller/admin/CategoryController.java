package com.youlai.mall.pms.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.youlai.common.enums.QueryModeEnum;
import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.domain.PmsCategory;
import com.youlai.mall.pms.pojo.vo.CategoryVO;
import com.youlai.mall.pms.service.IPmsAttributeService;
import com.youlai.mall.pms.service.IPmsCategoryService;
import com.youlai.mall.pms.service.IPmsSpecService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(tags = "【系统管理】商品分类")
@RestController
@RequestMapping("/api.admin/v1/categories")
@Slf4j
@AllArgsConstructor
public class CategoryController {

    private IPmsCategoryService iPmsCategoryService;
    private IPmsAttributeService iPmsAttributeService;
    private IPmsSpecService iPmsSpecService;

    @ApiOperation(value = "分类列表")
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

    @ApiOperation(value = "分类详情")
    @ApiImplicitParam(name = "id", value = "商品分类id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        PmsCategory category = iPmsCategoryService.getById(id);
        return Result.success(category);
    }

    @ApiOperation(value = "新增分类")
    @ApiImplicitParam(name = "category", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsCategory")
    @PostMapping
    public Result add(@RequestBody PmsCategory category) {
        iPmsCategoryService.save(category);
        CategoryVO categoryVO = new CategoryVO();
        BeanUtil.copyProperties(category, categoryVO);
        return Result.success(categoryVO);
    }

    @ApiOperation(value = "修改分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品分类id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "category", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsCategory")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Long id,
            @RequestBody PmsCategory category) {
        iPmsCategoryService.updateById(category);
        return Result.success(category);
    }

    @ApiOperation(value = "删除商品分类")
    @ApiImplicitParam(name = "ids", value = "id集合,以英文逗号','分隔", required = true, paramType = "query", dataType = "String")
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        Optional.ofNullable(idList).ifPresent(list -> {
            list.forEach(id -> {
                iPmsAttributeService.removeById(id);
                iPmsSpecService.removeById(id);
            });
            iPmsCategoryService.removeByIds(idList.stream().map(id -> Long.parseLong(id)).collect(Collectors.toList()));
        });
        return Result.success();
    }

    @ApiOperation(value = "修改分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "category", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsCategory")
    })
    @PatchMapping(value = "/{id}")
    public Result patch(@PathVariable Long id, @RequestBody PmsCategory category) {
        LambdaUpdateWrapper<PmsCategory> updateWrapper = new LambdaUpdateWrapper<PmsCategory>().eq(PmsCategory::getId, id);
        updateWrapper.set(category.getStatus() != null, PmsCategory::getStatus, category.getStatus());
        boolean update = iPmsCategoryService.update(updateWrapper);
        return Result.success(update);
    }
}
