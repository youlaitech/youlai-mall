package com.youlai.admin.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.youlai.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SysRole extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    @ApiModelProperty("角色编码")
    private String code;

    private Integer sort;

    private Integer status;

    @ApiModelProperty("逻辑删除标识 0-未删除 1-已删除")
    //@TableLogic(value = "0", delval = "1")
    private Integer deleted;

    @TableField(exist = false)
    private List<Long> menuIds;

    @TableField(exist = false)
    private List<Long> permissionIds;

}
