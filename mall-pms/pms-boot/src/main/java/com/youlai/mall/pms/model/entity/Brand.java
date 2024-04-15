package com.youlai.mall.pms.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.common.base.BaseEntity;
import lombok.Data;


@TableName("pms_brand")
@Data
public class Brand extends BaseEntity {

    private String name;

    private String logoUrl;

    private Integer sort;
}
