package com.youlai.admin.controller;

import com.youlai.admin.service.ITokenService;
import com.youlai.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author hxr
 * @date 2021-03-09
 */

@Api(tags = "令牌接口")
@RestController
@RequestMapping("/api.admin/v1/tokens")
@Slf4j
@AllArgsConstructor
public class TokenController {

    ITokenService tokenService;

    @ApiOperation(value = "强制下线")
    @ApiImplicitParam(name = "token", value = "访问令牌", required = true, paramType = "query", dataType = "String")
    @PostMapping("/{token}/_invalidate")
    @SneakyThrows
    public Result invalidateToken(@PathVariable String token) {
        boolean status = tokenService.invalidateToken(token);
        return Result.judge(status);
    }

}
