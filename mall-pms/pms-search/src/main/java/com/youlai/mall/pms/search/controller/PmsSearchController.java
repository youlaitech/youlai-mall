package com.youlai.mall.pms.search.controller;

import com.youlai.common.core.result.Result;
import com.youlai.mall.pms.search.service.IPmsSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Api(tags = "Pms搜索接口")
@RestController
@RequestMapping("/search")
@Slf4j
@AllArgsConstructor
public class PmsSearchController {

    private IPmsSearchService pmsSearchService;

    @ApiOperation(value = "根据文档id搜索商品", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "文档id", paramType = "query", dataType = "String"),
    })
    @GetMapping("/searchSpuById")
    public Result searchSpuById(@RequestParam("id") String id) throws IOException {
        return Result.success(pmsSearchService.searchSpuById(id));
    }

    @ApiOperation(value = "关键字搜索商品", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "关键字", paramType = "query", dataType = "String"),
    })
    @GetMapping("/searchSpuByKey")
    public Result searchSpuByKey(@RequestParam("key") String key) throws IOException {
        return Result.success(pmsSearchService.searchSpuByKey(key));
    }
}
