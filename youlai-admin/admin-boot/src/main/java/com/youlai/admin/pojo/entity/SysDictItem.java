package com.youlai.admin.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SysDictItem extends BaseEntity {

    @TableId(type= IdType.AUTO)
    private Long id;

    private String name;

    private String value;

    private String dictCode;

    private Integer sort;

    private Integer status;

    private Integer defaulted;

    private String remark;

}
