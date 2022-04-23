package com.youlai.admin.pojo.vo.menu;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.util.List;

@Data
public class MenuTableVO {

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

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private List<MenuTableVO> children;

}
