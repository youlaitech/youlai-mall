package com.fly4j.yshop.sms.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.fly4j.yshop.sms.pojo.dto.app.AppHomeAdDTO;
import com.fly4j.yshop.sms.pojo.entity.SmsHomeAd;
import com.fly4j.yshop.sms.service.ISmsHomeAdService;
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
@RequestMapping("/api.app/v1/adverts")
@Api(tags = "APP-营销广告API")
public class AppHomeAdController {

    @Autowired
    private ISmsHomeAdService iSmsHomeAdService;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;


    @ApiOperation(value = "广告列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "返回结果条数", paramType = "query", dataType = "Integer"),
    })
    @GetMapping()
    public R list(
            @RequestParam(required = false) Integer limit
    ) {
        List<SmsHomeAd> list = iSmsHomeAdService.list(new LambdaQueryWrapper<SmsHomeAd>()
                .orderByAsc(SmsHomeAd::getSort)
                .last(limit != null, "LIMIT " + limit)
        );
        List<AppHomeAdDTO> resultList = list.stream().map(item -> dozerBeanMapper.map(item, AppHomeAdDTO.class))
                .collect(Collectors.toList());
        return R.ok(resultList);
    }
}
