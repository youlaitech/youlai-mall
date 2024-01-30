package com.youlai.gateway.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 验证码响应对象
 *
 * @author haoxr
 * @since 2023/03/24
 */
@Schema(description ="验证码响应对象")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaptchaResult {

    @Schema(description = "验证码唯一标识(用于从Redis获取验证码Code)")
    private String captchaId;

    @Schema(description = "验证码图片Base64字符串")
    private String captchaBase64;

}
