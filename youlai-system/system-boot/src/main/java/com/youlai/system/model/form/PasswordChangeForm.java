package com.youlai.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 修改密码表单
 *
 * @author Ray
 * @since 2024/8/13
 */
@Schema(description = "修改密码表单")
@Data
public class PasswordChangeForm {

    @Schema(description = "原密码")
    private String oldPassword;

    @Schema(description = "新密码")
    private String newPassword;

}
