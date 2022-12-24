package com.youlai.mall.pms.controller.admin;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.youlai.common.result.Result;
import com.youlai.common.web.model.Option;
import com.youlai.mall.pms.pojo.entity.PmsCategoryAttribute;
import com.youlai.mall.pms.pojo.entity.PmsCategory;
import com.youlai.mall.pms.pojo.vo.CategoryVO;
import com.youlai.mall.pms.service.AttributeService;
import com.youlai.mall.pms.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * * 「管理端」商品分类控制器
 *
 * @author <a href="mailto:1490493387@qq.com">haoxr</a>
 * @date 2022/01/01
 */
@Api(tags = "「管理端」商品分类")
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class PmsCategoryController {

    private final CategoryService categoryService;
    private final AttributeService attributeService;

    @ApiOperation(value = "商品分类列表")
    @GetMapping
    public Result<List<CategoryVO>> list() {
        List<CategoryVO> list = categoryService.listCategory(null);
        return Result.success(list);
    }

    @ApiOperation(value = "商品分类级联列表")
    @GetMapping("/options")
    public Result listCategoryOptions() {
        List<Option> list = categoryService.listCategoryOptions();
        return Result.success(list);
    }

    @ApiOperation(value = "商品分类详情")
    @GetMapping("/{id}")
    public Result detail(
            @ApiParam("商品分类ID") @PathVariable Long id
    ) {
        PmsCategory category = categoryService.getById(id);
        return Result.success(category);
    }

    @ApiOperation(value = "新增商品分类")
    @PostMapping
    public Result addCategory(@RequestBody PmsCategory category) {
        Long id = categoryService.saveCategory(category);
        return Result.success(id);
    }

    @ApiOperation(value = "修改商品分类")
    @PutMapping(value = "/{id}")
    public Result update(
            @ApiParam("商品分类ID") @PathVariable Long id,
            @RequestBody PmsCategory category
    ) {
        category.setId(id);
        id = categoryService.saveCategory(category);
        return Result.success(id);
    }

    @ApiOperation(value = "删除商品分类")
    @ApiImplicitParam(name = "ids", value = "id集合,以英文逗号','分隔", required = true, paramType = "query", dataType = "String")
    @DeleteMapping("/{ids}")
    @CacheEvict(value = "pms", key = "'categoryList'")
    public Result delete(@PathVariable String ids) {
        List<String> categoryIds = Arrays.asList(ids.split(","));
        attributeService.remove(new LambdaQueryWrapper<PmsCategoryAttribute>().in(CollectionUtil.isNotEmpty(categoryIds),
                PmsCategoryAttribute::getCategoryId, categoryIds));
        boolean result = categoryService.removeByIds(categoryIds);
        return Result.judge(result);
    }

    @ApiOperation(value = "选择性修改商品分类")
    @PatchMapping(value = "/{id}")
    @CacheEvict(value = "pms", key = "'categoryList'")
    public Result patch(@PathVariable Long id, @RequestBody PmsCategory category) {
        LambdaUpdateWrapper<PmsCategory> updateWrapper = new LambdaUpdateWrapper<PmsCategory>()
                .eq(PmsCategory::getId, id);
        updateWrapper.set(category.getVisible() != null, PmsCategory::getVisible, category.getVisible());
        boolean result = categoryService.update(updateWrapper);
        return Result.judge(result);
    }
}
