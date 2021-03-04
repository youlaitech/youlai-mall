package com.youlai.mall.pms.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.base.BaseEntity;
import lombok.Data;

/**
 * @author haoxr
 * @date 2020-11-06
 */
@Data
public class PmsProductSpecValue extends BaseEntity {

    @TableId(type = IdType.INPUT)
    private Long id;
    private Long productId;
    private Long categorySpecId;
    private String value;
}
