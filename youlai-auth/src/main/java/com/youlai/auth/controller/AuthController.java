package com.youlai.auth.controller;

import com.youlai.auth.model.CaptchaResult;
import com.youlai.auth.service.AuthService;
import com.youlai.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证控制器
 * <p>
 * 获取验证码、退出登录等接口
 * 注：登录接口不在此控制器，在过滤器OAuth2TokenEndpointFilter拦截端点(/oauth2/token)处理
 *
 * @author haoxr
 * @since 3.1.0
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @Operation(summary = "获取验证码")
    @GetMapping("/captcha")
    public Result<CaptchaResult> getCaptcha() {
        CaptchaResult captchaResult = authService.getCaptcha();
        return Result.success(captchaResult);
    }

    @Operation(summary = "注销登出")
    @DeleteMapping("/logout")
    public Result logout() {
        boolean result = authService.logout();
        return Result.judge(result);
    }


}
