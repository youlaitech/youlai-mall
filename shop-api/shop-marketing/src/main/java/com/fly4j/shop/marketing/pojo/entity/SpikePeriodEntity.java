package com.fly4j.shop.marketing.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly4j.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
@TableName("m_spike_period")
public class SpikePeriodEntity extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    @JsonFormat(pattern = "HH:mm", timezone = "GMT-8")
    private Date startTime;

    @JsonFormat(pattern = "HH:mm", timezone = "GMT-8")
    private Date endTime;

    private Integer status;

}
