package com.youlai.mall.pms.controller;

import com.youlai.common.core.result.Result;
import com.youlai.mall.pms.pojo.PmsAttribute;
import com.youlai.mall.pms.pojo.PmsCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "商品属性接口")
@RestController
@RequestMapping("/attributes")
@Slf4j
@AllArgsConstructor
public class PmsAttributeController {


    @ApiOperation(value = "新增商品属性", httpMethod = "POST")
    @ApiImplicitParam(name = "attribute", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsAttribute")
    @PostMapping
    public Result save(@RequestBody List<PmsAttribute> attributes) {


        return Result.success();
    }

}
