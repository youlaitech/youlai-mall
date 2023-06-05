package com.youlai.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "部门表单对象")
@Data
public class DeptForm {

    @Schema(description="部门ID")
    private Long id;

    @Schema(description="部门名称")
    private String name;

    @Schema(description="父部门ID")
    @NotNull(message = "父部门ID不能为空")
    private Long parentId;

    @Schema(description="状态(1:启用;0:禁用)")
    private Integer status;

    @Schema(description="排序(数字越小排名越靠前)")
    private Integer sort;

}
