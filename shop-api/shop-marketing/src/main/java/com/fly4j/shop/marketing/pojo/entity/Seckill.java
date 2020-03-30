package com.fly4j.shop.marketing.pojo.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly4j.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
@TableName("m_seckill")
public class Seckill extends BaseEntity {

    @TableId
    private Long id;

    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;

    private Integer status;

}
