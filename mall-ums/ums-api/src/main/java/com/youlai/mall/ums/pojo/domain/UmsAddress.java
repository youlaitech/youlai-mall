package com.youlai.mall.ums.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UmsAddress extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long memberId;

    private String name;

    private String mobile;

    private String province;

    private String city;

    private String area;

    private String address;

    private String zipCode;

    private Integer defaulted;
}
