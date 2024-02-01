package com.youlai.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

/**
 * 用户注册表单
 *
 * @author haoxr
 * @since 3.1.0
 */
@Schema(description = "用户注册表单")
@Data
public class UserRegisterForm {

    @Schema(description="登录账号")
    @NotBlank(message = "登录账号不能为空")
    private String username;

    @Schema(description="手机号码")
    @Pattern(regexp = "^$|^1(3\\d|4[5-9]|5[0-35-9]|6[2567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$", message = "手机号码格式不正确")
    private String mobile;

    @Schema(description="密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @Schema(description="验证码")
    @NotBlank(message = "验证码不能为空")
    private String code;

}
