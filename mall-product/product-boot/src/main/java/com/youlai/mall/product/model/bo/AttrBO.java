package com.youlai.mall.product.model.bo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.youlai.mall.product.enums.AttributeInputTypeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 属性业务对象
 *
 * @author Ray.Hao
 * @since 2024-04-19
 */
@Getter
@Setter
public class AttrBO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 属性主键
     */
    private Long id;

    /**
     * 属性组主键
     */
    private Long attrGroupId;

    /**
     * 属性名称
     */
    private String name;

    /**
     * 输入方式（1：手动输入，2：从列表选择）
     */
    private AttributeInputTypeEnum inputType;

    /**
     * 可选值列表（以逗号分隔，仅当输入方式为2时使用）
     */
    private String options;

    /**
     * 属性组名称
     */
    private String attributeGroupName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 排序
     */
    private Integer sort;


}
