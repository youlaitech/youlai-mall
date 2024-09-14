package com.youlai.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * 字典项实体
 *
 * @author haoxr
 * @since 0.0.1
 */
@Data
@TableName("sys_dict_item")
public class DictItem  {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 字典类ID
     */
    private Long dictId;

    /**
     * 字典项名称
     */
    private String name;

    /**
     * 字典项值
     */
    private String value;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态（1-正常，0-禁用）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
}