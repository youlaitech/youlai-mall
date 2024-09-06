package com.youlai.codegen.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Schema(description = "表视图对象")
@Data
public class TablePageVO {

    @Schema(description = "表名称", example = "sys_user")
    private String tableName;

    @Schema(description = "表描述",example = "用户表")
    private String tableComment;

    @Schema(description = "表排序规则",example = "utf8mb4_general_ci")
    private String tableCollation;

    @Schema(description = "存储引擎",example = "InnoDB")
    private String engine;

    @Schema(description = "字符集",example = "utf8mb4")
    private String charset;

    @Schema(description = "创建时间",example = "2023-08-08 08:08:08")
    private String createTime;

    @Schema(description="是否已配置")
    private Integer isConfigured;

}
