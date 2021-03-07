package com.youlai.mall.pms.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.base.BaseEntity;
import lombok.Data;


@Data
public class PmsBrand extends BaseEntity {

    @TableId(type= IdType.AUTO)
    private Long id;

    private String name;

    private String logo;

    private String firstLetter;

    private Integer sort;

    private Integer status;
}
