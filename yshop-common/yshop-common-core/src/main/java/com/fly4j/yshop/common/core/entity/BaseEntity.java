package com.fly4j.yshop.common.core.entity;

<<<<<<< HEAD
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
=======
import io.swagger.annotations.ApiModel;
>>>>>>> 3d26641c4a5a111308766367225743ff672555c3
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

<<<<<<< HEAD
=======
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

>>>>>>> 3d26641c4a5a111308766367225743ff672555c3
@Data
@ApiModel
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

<<<<<<< HEAD
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人",example = "fly4j")
    private String create_by;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间",example = "2020-01-01 12:00:00")
    private Date create_time;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改人",example = "fly4j")
    private String update_by;

    @TableField(fill = FieldFill.INSERT_UPDATE)
=======
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
>>>>>>> 3d26641c4a5a111308766367225743ff672555c3
    @ApiModelProperty(value = "创建时间",example = "2020-01-01 12:00:00")
    private Date update_time;
}
