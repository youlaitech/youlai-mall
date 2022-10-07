package com.youlai.system.pojo.query;

import com.youlai.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色分页查询对象
 *
 * @author haoxr
 * @date 2022/6/3
 *
 */
@ApiModel
@Data
public class RolePageQuery extends BasePageQuery {

    @ApiModelProperty("关键字(角色名称/角色编码)")
    private String keywords;

    @ApiModelProperty("角色状态")
    private Integer status;
}
