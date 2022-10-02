package com.youlai.system.pojo.vo.perm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 权限视图对象
 *
 * @author haoxr
 * @date 2021/10/30 10:54
 */
@ApiModel("权限视图对象")
@Data
public class PermPageVO {

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
