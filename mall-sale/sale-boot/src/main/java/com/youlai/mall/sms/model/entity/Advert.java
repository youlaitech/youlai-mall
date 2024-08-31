package com.youlai.mall.sms.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.youlai.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 营销广告
 *
 * @author Ray Hao
 * @since 2024/6/7
 */
@EqualsAndHashCode(callSuper = true)
@TableName("sms_advert")
@Data
public class Advert extends BaseEntity {

    private String title;

    private String imageUrl;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endTime;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 跳转URL
     */
    private String redirectUrl;

    private String remark;

}
