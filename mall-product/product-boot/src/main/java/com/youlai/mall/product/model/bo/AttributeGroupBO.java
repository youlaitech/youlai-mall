package com.youlai.mall.product.model.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 属性组业务对象
 *
 * @author Ray Hao
 * @since 2024-04-19
 */
@Getter
@Setter
public class AttributeGroupBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 属性组主键
     */
    private Long id;

    /**
     * 属性组名称
     */
    private String name;

    /**
     * 排序
     */
    private Short sort;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 分类Label
     */
    private String categoryLabel;

}
