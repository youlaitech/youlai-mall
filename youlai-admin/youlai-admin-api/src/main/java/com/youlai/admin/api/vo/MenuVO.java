package com.youlai.admin.api.vo;

import com.youlai.common.core.base.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
public class MenuVO extends BaseEntity {

    private Integer id;

    private String name;

    private Integer parentId;

    private String icon;

    private Integer sort;

    private Integer visible;

    private Integer status;

    private String component;

    private String path;

    private String perms;

    private List<MenuVO> children;

}
