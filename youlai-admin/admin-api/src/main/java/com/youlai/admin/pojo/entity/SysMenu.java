package com.youlai.admin.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.youlai.common.core.base.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * @author haoxr
 * @date 2020-11-06
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@TableName(value = "sys_menu_test")
public class SysMenu extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private Long parentId;

    private Integer type;

    private String path;

    private String redirect;

    private String icon;

    private Integer sort;

    private Integer visible;

    private Integer status;

    @TableField(exist = false)
    private List<Integer> roles;

}
