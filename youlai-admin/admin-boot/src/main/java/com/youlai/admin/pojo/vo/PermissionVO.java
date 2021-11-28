package com.youlai.admin.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2021/10/30 10:54
 */
@Data
@ApiModel("权限视图对象")
public class PermissionVO {

    @ApiModelProperty("权限ID")
    private Long id;

    @ApiModelProperty("权限名称")
    private String name;

    @ApiModelProperty("URL权限标识-服务名称")
    private String serviceName;

    @ApiModelProperty("URL权限标识-请求标识")
    private String requestMethod;

    @ApiModelProperty("URL权限标识-请求方式")
    private String requestPath;

    @ApiModelProperty("按钮权限标识")
    private String btnPerm;

}
