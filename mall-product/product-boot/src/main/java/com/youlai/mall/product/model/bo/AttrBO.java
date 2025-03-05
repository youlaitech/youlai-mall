package com.youlai.mall.product.model.bo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

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
     * 属性组名称
     */
    private String groupName;

    /**
     * 属性名称
     */
    private String name;

    /**
     * 输入方式类型(1:文本 2:单选 3:多选)
     */
    private Integer inputType;

    /**
     * 可选项(逗号分隔)
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
     * 排序
     */
    private Integer sort;


}
