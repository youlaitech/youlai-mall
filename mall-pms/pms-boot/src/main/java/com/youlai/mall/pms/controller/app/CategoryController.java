package com.youlai.mall.pms.controller.app;

import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.vo.CategoryVO;
import com.youlai.mall.pms.service.IPmsCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品分类控制器
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/2/5
 */
@Api(tags = "「移动端」商品分类")
@RestController("appCategoryController")
@RequestMapping("/app-api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final IPmsCategoryService iPmsCategoryService;

    @ApiOperation(value = "分类列表")
    @GetMapping
    public Result list(
           @ApiParam("上级分类ID") Long parentId) {
        List<CategoryVO> list = iPmsCategoryService.listCategory(parentId);
        return Result.success(list);
    }
}
