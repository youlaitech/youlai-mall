package com.youlai.mall.pms.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.base.BaseEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PmsCategorySpec extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long categoryId;
    private String name;

    @TableField(exist = false)
    private List<PmsProductSpecValue> values = new ArrayList<>();

}
