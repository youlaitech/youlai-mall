package com.youlai.admin.pojo.vo.menu;

import lombok.Data;

@Data
public class MenuDetailVO {

    private Long id;

    private Long parentId;

    private String name;

    private String icon;

    private String routeName;

    private String routePath;

    private String component;

    private Integer sort;

    private Integer visible;

    private String redirect;

    private Integer type;

}
