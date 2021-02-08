package com.youlai.mall.sms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.base.BaseEntity;
import lombok.Data;

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
