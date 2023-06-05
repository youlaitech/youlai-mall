package com.youlai.common.sms.controller;

import com.youlai.common.result.Result;
import com.youlai.common.sms.service.AliyunSmsService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "短信验证码")
@RestController
@RequestMapping("/sms-code")
@RequiredArgsConstructor
public class SmsCodeController {

    private final AliyunSmsService aliyunSmsService;

    @Operation(summary = "发送短信验证码")
    @PostMapping
    public Result sendSmsCode(
            @Parameter(name = "手机号") String phoneNumber
    ) {
        boolean result = aliyunSmsService.sendSmsCode(phoneNumber);
        return Result.judge(result);
    }
}
