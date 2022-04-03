package com.youlai.admin.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.youlai.common.base.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * @author haoxr
 * @date 2020-11-06
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SysMenu extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long parentId;

    private String name;

    private String icon;

    /**
     * 路由相对路径
     */
    private String path;

    /**
     * 组件绝对路径
     */
    private String component;

    private Integer sort;

    private Integer visible;

    private String redirect;

    @TableField(exist = false)
    private List<String> roles;

}
