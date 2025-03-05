package com.youlai.mall.product.model.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * 规格业务对象
 *
 * @author Ray.Hao
 * @since 2024-06-13
 */
@Getter
@Setter
public class SpecBO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 属性名称
     */
    private String name;

    /**
     * 输入类型(1:文本 2:单选 3:多选)
     */
    private Integer inputType;

    /**
     * 可选项(逗号分隔)
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
     * 逻辑删除标识(0:未删除 1:已删除)
     */
    private Byte isDeleted;
}
