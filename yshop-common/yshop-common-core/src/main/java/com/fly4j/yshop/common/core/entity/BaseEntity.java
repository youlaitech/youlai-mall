package com.fly4j.yshop.common.core.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
@ApiModel
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    // @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人",example = "fly4j")
    private String create_by;

    // @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间",example = "2020-01-01 12:00:00")
    private Date create_time;

    // @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改人",example = "fly4j")
    private String update_by;

    // @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "创建时间",example = "2020-01-01 12:00:00")
    private Date update_time;
}
