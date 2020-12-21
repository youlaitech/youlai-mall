package com.youlai.mall.pms.controller;

import com.youlai.common.core.result.Result;
import com.youlai.mall.pms.pojo.PmsSpecification;
import com.youlai.mall.pms.service.IPmsSpecificationService;
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

@Api(tags = "规格接口")
@RestController
@RequestMapping("/specifications")
@Slf4j
@AllArgsConstructor
public class PmsSpecificationController {

    private IPmsSpecificationService iPmsSpecificationService;

    @ApiOperation(value = "新增规格", httpMethod = "POST")
    @ApiImplicitParam(name = "specifications", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsSpecification")
    @PostMapping
    public Result save(@RequestBody List<PmsSpecification> specifications) {
        boolean result = iPmsSpecificationService.saveOrUpdateBatch(specifications);
        return Result.status(result);
    }
}
