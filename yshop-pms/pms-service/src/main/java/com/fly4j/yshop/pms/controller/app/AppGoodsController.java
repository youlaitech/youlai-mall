package com.fly4j.yshop.pms.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.fly4j.yshop.pms.pojo.dto.app.AppCategoryDTO;
import com.fly4j.yshop.pms.pojo.dto.app.AppGoodsDTO;
import com.fly4j.yshop.pms.pojo.dto.app.AppGoodsDetailDTO;
import com.fly4j.yshop.pms.pojo.entity.PmsCategory;
import com.fly4j.yshop.pms.pojo.entity.PmsSpu;
import com.fly4j.yshop.pms.service.app.IAppSpuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * APP商品API
 *
 * @author haoxianrui
 * @since 2020-04-20
 **/

@RestController
@RequestMapping("/api.app/v1/goods")
@Api(tags = "APP-商品API")
public class AppGoodsController {

    @Autowired
    private IAppSpuService iAppSpuService;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;


    @ApiOperation(value = "商品列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_id", value = "商品分类ID", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "limit", value = "返回结果条数", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "is_hot", value = "是否热品", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "is_new", value = "是否新品", paramType = "query", dataType = "Integer"),
    })
    @GetMapping()
    public R list(
            @RequestParam(required = false) Long category_id,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Long is_hot,
            @RequestParam(required = false) Long is_new
    ) {
        List<PmsSpu> list = iAppSpuService.list(new LambdaQueryWrapper<PmsSpu>()
                .eq(category_id != null, PmsSpu::getCategory_id, category_id)
                .eq(is_hot != null, PmsSpu::getIs_hot, is_hot)
                .eq(is_new != null, PmsSpu::getIs_new, is_new)
                .orderByAsc(PmsSpu::getSort)
                .last(limit != null, "LIMIT "+limit)
        );
        List<AppGoodsDTO> resultList = list.stream().map(item -> dozerBeanMapper.map(item, AppGoodsDTO.class)).collect(Collectors.toList());
        return R.ok(resultList);
    }

    @ApiOperation(value = "商品详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品ID", required = true, paramType = "path", dataType = "Long"),
    })
    @GetMapping("/{id}")
    public R<AppGoodsDetailDTO> detail(@PathVariable Long id){
        AppGoodsDetailDTO appGoodsDetailDTO = iAppSpuService.getGoodsDetail(id);
        return R.ok(appGoodsDetailDTO);
    }
}
