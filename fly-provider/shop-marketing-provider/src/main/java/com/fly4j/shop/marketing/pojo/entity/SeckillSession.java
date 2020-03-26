package com.fly4j.shop.marketing.pojo.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
@TableName("m_seckill_session")
public class SeckillSession extends BaseEntity {

    @TableId
    private Long id;

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT-8")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT-8")
    private Date endTime;

    private Integer status;

}
