package com.youlai.mall.pms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.core.base.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
public class PmsSku extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long spuId;
    private String name;
    private String code;
    private String picUrl;
    private Long originPrice;
    private Long price;
    private Integer stock;
    private String specValueIds;
}
