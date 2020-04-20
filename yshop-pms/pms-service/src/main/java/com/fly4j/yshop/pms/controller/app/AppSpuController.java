package com.fly4j.yshop.pms.controller.app;

import com.baomidou.mybatisplus.extension.api.R;
import com.fly4j.yshop.pms.pojo.dto.app.AppSpuDTO;
import com.fly4j.yshop.pms.service.app.IAppSpuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * APP商品接口
 *
 * @author haoxianrui
 * @since 2020-04-20
 **/

@RestController
@RequestMapping("/api.app/v1/spus")
@Api(tags = "手机APP端-商品接口")
public class AppSpuController {


    @Autowired
    private IAppSpuService iAppSpuService;


    @ApiOperation(value = "商品详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品ID", required = true, paramType = "path", dataType = "Long"),
    })
    @GetMapping("/{id}")
    public R<AppSpuDTO> detail(@PathVariable Long id){
        AppSpuDTO appSpuDTO = iAppSpuService.getSpuDetail(id);
        return R.ok(appSpuDTO);
    }
}
