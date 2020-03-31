package com.fly4j.shop.marketing.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly4j.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 系统首页广告实体
 *
 * @author fly2021【xianrui0365@163.com】
 * @date 2020-03-31 16:27
 **/

@Data
@Accessors(chain = true)
@TableName("m_home_advertisement")
public class HomeAdvertisementEntity extends BaseEntity {

    @TableId(type = IdType.AUTO)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String name;

    private Integer type;

    private String imageUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    private Integer status;

    private String linkUrl;

    private String remark;

    private Integer sort;

}
