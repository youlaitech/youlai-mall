package com.youlai.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.youlai.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色实体类
 *
 * @author haoxr
 * @date 2022/10/6
 */
@Data
public class SysRole extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    @ApiModelProperty("角色编码")
    private String code;

    private Integer sort;

    private Integer status;

    @ApiModelProperty("逻辑删除标识(1:已删除;0:未删除)")
    @TableLogic(value = "0", delval = "1")
    private Integer deleted;
}
