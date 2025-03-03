package com.youlai.mall.product.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * 实体
 *
 * @author Ray.Hao
 * @since 2024-06-13
 */
@Getter
@Setter
@TableName("pms_spec")
public class Spec implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 属性名称
     */
    private String name;

    /**
     * 输入方式：1->手动输入，2->列表选择
     */
    private Integer inputType;

    /**
     * 可选值列表（以逗号分隔，仅当输入方式为2时使用）
     */
    private String options;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 排序
     */
    private Short sort;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 逻辑删除标识（0：未删除，1：已删除）
     */
    private Integer isDeleted;
}
