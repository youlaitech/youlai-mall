package com.youlai.mall.ums.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.core.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class UmsMemberAddress extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long memberId;

    private String receiverName;

    private String receiverMobile;

    private String province;

    private String city;

    private  String  area;

    private String addressDetail;

    private String zipCode;

    private Integer defaulted;
}
