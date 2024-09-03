package com.youlai.system.model.form;

import com.youlai.system.enums.FormTypeEnum;
import com.youlai.system.enums.QueryTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 代码生成配置表单
 *
 * @author Ray
 * @since 2.10.0
 */
@Schema(description = "代码生成配置表单")
@Data
public class GenConfigForm {

    @Schema(description = "主键",example = "1")
    private Long id;

    @Schema(description = "表名",example = "sys_user")
    private String tableName;

    @Schema(description = "业务名",example = "用户")
    private String businessName;

    @Schema(description = "模块名",example = "system")
    private String moduleName;

    @Schema(description = "包名",example = "com.youlai")
    private String packageName;

    @Schema(description = "实体名",example = "User")
    private String entityName;

    @Schema(description = "作者",example = "youlaitech")
    private String author;

    @Schema(description = "上级菜单ID",example = "1")
    private Long parentMenuId;

    @Schema(description = "字段配置列表")
    private List<FieldConfig> fieldConfigs;

    @Schema(description = "后端应用名")
    private String backendAppName;

    @Schema(description = "前端应用名")
    private String frontendAppName;

    @Schema(description = "字段配置")
    @Data
    public static class FieldConfig {

        @Schema(description = "主键")
        private Long id;

        @Schema(description = "列名")
        private String columnName;

        @Schema(description = "列类型")
        private String columnType;

        @Schema(description = "字段名")
        private String fieldName;

        @Schema(description = "字段排序")
        private Integer fieldSort;

        @Schema(description = "字段类型")
        private String fieldType;

        @Schema(description = "字段描述")
        private String fieldComment;

        @Schema(description = "是否在列表显示")
        private Integer isShowInList;

        @Schema(description = "是否在表单显示")
        private Integer isShowInForm;

        @Schema(description = "是否在查询条件显示")
        private Integer isShowInQuery;

        @Schema(description = "是否必填")
        private Integer isRequired;

        @Schema(description = "最大长度")
        private Integer maxLength;

        @Schema(description = "表单类型")
        private FormTypeEnum formType;

        @Schema(description = "查询类型")
        private QueryTypeEnum queryType;

        @Schema(description = "字典类型")
        private String dictType;

    }
}
