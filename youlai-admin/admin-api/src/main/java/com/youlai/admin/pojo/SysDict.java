package com.youlai.admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.core.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SysDict  extends BaseEntity {

    @TableId(type= IdType.AUTO)
    private Long id;

    private String name;

    private String value;

    private String typeCode;

    private String sort;

    private Integer status;

    private Integer defaulted;

    private String remark;

}
