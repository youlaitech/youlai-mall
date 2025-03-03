package com.youlai.mall.product.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 属性实体
 *
 * @author Ray.Hao
 * @since 2024/5/24
 */
@Getter
@Setter
@TableName("pms_attr")
public class Attr extends BaseEntity {

    /**
     * 属性名称
     */
    private String name;

    /**
     * 属性组名称
     */
    private String groupName;

    /**
     * 输入类型(1:文本 2:单选 3:多选)
     */
    private Integer inputType;

    /**
     * 可选项(逗号分隔)
     */
    private String options;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 逻辑删除标识(0:未删除 1:已删除)
     */
    private Integer isDeleted;

}
