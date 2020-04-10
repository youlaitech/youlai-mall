package com.fly4j.shop.marketing.pojo.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly4j.common.core.bean.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
@TableName("m_spike_record")
public class SpikeRecordEntity extends BaseEntity {

    @TableId
    private Long id;

    private String memberId;

    private String memberPhone;

    private Long goodsId;

    private String goodsName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT-8")
    private Date subscribeTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT-8")
    private Date sendTime;

}
