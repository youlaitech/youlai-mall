package com.youlai.mall.pms.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * 属性组
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
@Getter
@Setter
@TableName("pms_attribute_group")
public class AttributeGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 属性组主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     * 更新时间
     */
    private LocalDateTime updateTime;
}
