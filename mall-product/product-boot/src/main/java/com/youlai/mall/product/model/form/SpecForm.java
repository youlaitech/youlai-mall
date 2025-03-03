package com.youlai.mall.product.model.form;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 *  表单对象
 *
 * @author Ray.Hao
 * @since 2024-06-13
 */
@Getter
@Setter
@Schema(description = "表单对象")
public class SpecForm implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;

    @Schema(description = "属性名称")
    private String name;

    @Schema(description = "输入类型(1:文本 2:单选 3:多选)")
    private Byte inputType;

    @Schema(description = "可选项(逗号分隔)")
    private String options;

    @Schema(description = "分类ID")
    private Long categoryId;

    @Schema(description = "排序")
    private Short sort;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "逻辑删除标识（0：未删除，1：已删除）")
    private Byte isDeleted;
}
