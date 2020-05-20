package com.fly4j.yshop.pms.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.fly4j.yshop.pms.service.IPmsCategoryService;
import com.fly4j.yshop.pms.pojo.dto.app.AppCategoryDTO;
import com.fly4j.yshop.pms.pojo.entity.PmsCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author haoxianrui
 * @since 2020-04-21
 **/

@RestController
@RequestMapping("/api.app/v1/categories")
<<<<<<< HEAD
@Api(tags = "APP-商品分类API")
=======
@Api(tags = "APP-商品分类")
>>>>>>> 3d26641c4a5a111308766367225743ff672555c3
public class AppCategoryController {

    @Autowired
    private IPmsCategoryService iPmsCategoryService;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;


    @ApiOperation(value = "商品分类", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "返回结果条数", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "level", value = "分类级别", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "parent_id", value = "父分类ID", paramType = "query", dataType = "Long"),
    })
    @GetMapping()
    public R list(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer level,
            @RequestParam(required = false) Long parent_id
    ) {
        List<PmsCategory> list = iPmsCategoryService.list(new LambdaQueryWrapper<PmsCategory>()
                .eq(level != null, PmsCategory::getLevel, level)
                .eq(parent_id != null, PmsCategory::getParent_id, parent_id)
                .orderByAsc(PmsCategory::getSort)
                .last(limit != null, "LIMIT " + limit)
        );
        List<AppCategoryDTO> resultList = list.stream().map(item -> dozerBeanMapper.map(item, AppCategoryDTO.class)).collect(Collectors.toList());
        return R.ok(resultList);
    }
}
