package com.youlai.mall.pms.controller.app;

import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.vo.CategoryVO;
import com.youlai.mall.pms.service.IPmsCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:xianrui0365@163.com">xianrui</a>
 */
@Api(tags = "移动端-商品分类")
@RestController("appCategoryController")
@RequestMapping("/app-api/v1/categories")
@Slf4j
@AllArgsConstructor
public class CategoryController {

    private IPmsCategoryService iPmsCategoryService;

    @ApiOperation(value = "分类列表")
    @ApiImplicitParam(name = "parentId", paramType = "query", dataType = "Long")
    @GetMapping
    public Result list(Long parentId) {
        List<CategoryVO> list = iPmsCategoryService.listTreeCategory(parentId);
        return Result.success(list);
    }
}
