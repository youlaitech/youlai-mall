package com.youlai.system.pojo.vo.menu;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.youlai.common.enums.MenuTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@ApiModel("菜单视图对象")
@Data
public class MenuVO {

    private Long id;

    private Long parentId;

    private String name;

    private String icon;

    private String routeName;

    private String routePath;

    private String component;

    private Integer sort;

    private Integer visible;

    @ApiModelProperty("权限标识")
    private String perm;

    private String redirect;

    @ApiModelProperty("菜单类型")
    private MenuTypeEnum type;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private List<MenuVO> children;

   @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime updateTime;

}