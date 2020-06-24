package com.fly4j.yshop.pms.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.youlai.common.core.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("pms_brand")
public class PmsBrand extends BaseEntity {

    @TableId(type = IdType.ID_WORKER)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String name;
    private String description;
    private String logo_url;
    private String pic_url;
    private Integer sort;
    private Integer status;
    private String first_letter;
}
