package com.youlai.admin.controller;

import com.youlai.admin.pojo.vo.RouteVO;
import com.youlai.admin.service.ISysMenuService;
import com.youlai.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xianrui
 * @date 2021/6/1 22:40
 */
@Api(tags = "路由接口")
@RestController
@RequestMapping("/api/v1/routes")
@Slf4j
@AllArgsConstructor
public class RouteController {

    private ISysMenuService iSysMenuService;

    @ApiOperation(value = "路由列表")
    @GetMapping
    public Result list() {
        List<RouteVO> list = iSysMenuService.listRoute();
        return Result.success(list);
    }
}


