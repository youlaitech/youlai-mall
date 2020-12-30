package com.youlai.mall.pms.controller.app;

import com.youlai.common.core.constant.SystemConstants;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "分类接口【移动端】")
@RestController
@RequestMapping("/api.app/v1/categories")
@Slf4j
@AllArgsConstructor
public class AppCategoryController {

    private IPmsCategoryService iPmsCategoryService;

    @ApiOperation(value = "分类列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", paramType = "query", dataType = "Long")
    })
    @GetMapping
    public Result list(Long parentId) {
        PmsCategory category = new PmsCategory();
        category.setParentId(parentId);
        category.setStatus(SystemConstants.STATUS_NORMAL_VALUE);
        List<CategoryVO> list = iPmsCategoryService.listForTree(category);
        return Result.success(list);
    }
}
