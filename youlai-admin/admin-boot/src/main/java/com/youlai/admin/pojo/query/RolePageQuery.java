package com.youlai.admin.pojo.query;

import com.youlai.common.base.BasePageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色分页查询实体
 *
 * @author haoxr
 * @date 2022/6/3
 *
 */
@Data
public class RolePageQuery extends BasePageQuery {

    @ApiModelProperty("角色名称")
    private String name;
}
