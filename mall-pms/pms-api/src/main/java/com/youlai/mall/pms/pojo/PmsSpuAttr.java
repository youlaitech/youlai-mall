package com.youlai.mall.pms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.core.base.BaseEntity;
import lombok.Data;

/**
 * @author haoxr
 * @date 2020-11-06
 */
@Data
public class PmsSpuAttr extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long spuId;
    private Long attrCategoryId;
    private String value;

    @TableField(exist = false)
    private String name;
}
