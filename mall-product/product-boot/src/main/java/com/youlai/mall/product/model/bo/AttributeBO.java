package com.youlai.mall.product.model.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * 属性
 *
 * @author Ray Hao
 * @since 2024-04-19
 */
@Getter
@Setter
public class AttributeBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 属性主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 属性组主键
     */
    private Long attributeGroupId;

    /**
     * 属性名称
     */
    private String name;

    /**
     * 输入录入方式：1-手动输入，2-从列表选择
     */
    private Integer  inputType;

    /**
     * 逗号分割的可选值列表，仅当input_type是2使用
     */
    private String options;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 逻辑删除标识(0-未删除，1-已删除)
     */
    private Integer isDeleted;;
}
