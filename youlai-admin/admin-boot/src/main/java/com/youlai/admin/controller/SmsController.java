package com.youlai.admin.controller;

import com.youlai.admin.service.ISmsService;
import com.youlai.common.result.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:xianrui0365@163.com">xianrui</a>
 * @date 2021/10/6
 */
@RestController
@RequestMapping("/api/v1/sms")
@RequiredArgsConstructor
public class SmsController {

    private final ISmsService smsService;

    @PostMapping("/code")
    @ApiOperation(value = "发送短信验证码")
    @ApiImplicitParam(name = "phoneNumber", value = "11位手机号", example = "15951933116",paramType = "query", dataType = "string")
    public Result sendSmsCode(String phoneNumber) {
        boolean result = smsService.sendSmsCode(phoneNumber);
        return Result.success(result);
    }

}
