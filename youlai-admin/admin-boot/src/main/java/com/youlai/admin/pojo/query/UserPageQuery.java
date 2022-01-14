package com.youlai.admin.pojo.query;

import com.youlai.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户分页查询对象
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/1/14 22:22
 */
@Data
@ApiModel
public class UserPageQuery extends BasePageQuery {

    @ApiModelProperty("关键字(用户名、昵称、手机号)")
    private String keywords;

    @ApiModelProperty("用户状态")
    private Integer status;

    @ApiModelProperty("部门ID")
    private Long deptId;

}
