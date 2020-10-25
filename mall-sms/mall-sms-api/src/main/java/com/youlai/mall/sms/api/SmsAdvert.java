package com.youlai.mall.sms.api;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.core.base.BaseEntity;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class SmsAdvert extends BaseEntity {

    @TableId(type= IdType.AUTO)
    private Integer id;

    private String name;

    private String pic;

    private Date startTime;

    private Date endTime;

    private Integer status;

    private Integer sort;

    private  String url;

    private String remark;


}
