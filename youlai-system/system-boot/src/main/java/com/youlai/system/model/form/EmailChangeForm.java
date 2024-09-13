package com.youlai.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 修改邮箱表单
 *
 * @author Ray
 * @since 2024/8/19
 */
@Schema(description = "修改邮箱表单")
@Data
public class EmailChangeForm {

    @Schema(description = "原密码")
    private String email;

    @Schema(description = "验证码")
    private String code;

}
