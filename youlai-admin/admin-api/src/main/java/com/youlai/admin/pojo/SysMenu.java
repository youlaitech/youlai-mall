package com.youlai.admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class SysMenu extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String title;

    private Long parentId;

    private String name;

    private String path;

    private String icon;

    private Integer sort;

    private String component;

    private String redirect;

    private Integer visible;

    private Integer status;

    @TableField(exist = false)
    private List<Integer> roles;

}
