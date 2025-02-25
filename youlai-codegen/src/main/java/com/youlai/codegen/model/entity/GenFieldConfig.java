package com.youlai.codegen.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.youlai.codegen.enums.FormTypeEnum;
import com.youlai.codegen.enums.QueryTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 字段生成配置实体
 *
 * @author Ray.Hao
 * @since 2.10.0
 */
@TableName(value = "gen_field_config")
@Getter
@Setter
public class GenFieldConfig {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;


    /**
     * 关联的配置ID
     */
    private Long configId;

    /**
     * 列名
     */
    private String columnName;

    /**
     * 列类型
     */
    private String columnType;

    /**
     * 字段长度
     */
    private Integer maxLength;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 字段排序
     */
    private Integer fieldSort;

    /**
     * 字段类型
     */
    private String fieldType;

    /**
     * 字段描述
     */
    private String fieldComment;

    /**
     * 表单类型
     */
    private FormTypeEnum formType;

    /**
     * 查询方式
     */
    private QueryTypeEnum queryType;

    /**
     * 是否在列表显示
     */
    private Integer isShowInList;

    /**
     * 是否在表单显示
     */
    private Integer isShowInForm;

    /**
     * 是否在查询条件显示
     */
    private Integer isShowInQuery;

    /**
     * 是否必填
     */
    private Integer isRequired;

    /**
     * TypeScript类型
     */
    @TableField(exist = false)
    @JsonIgnore
    private String tsType;

    /**
     * 字典类型
     */
    private String dictType;


    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}