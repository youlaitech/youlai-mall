package com.fly4j.yshop.ums.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly4j.common.core.bean.BaseEntity;
import lombok.Data;

@Data
public class UmsAddress extends BaseEntity {
    @TableId(type = IdType.ID_WORKER)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long member_id;
    private String province;
    private String city;
    private String area;
    private String address_detail;
    private String area_code;
    private String postal_code;
    private String tel;
    private Integer is_default;
}
