package com.youlai.mall.pms.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class PmsAttribute {

    @TableId(type=IdType.AUTO)
    private Long id;
    private Long categoryId;
    private String name;

}
