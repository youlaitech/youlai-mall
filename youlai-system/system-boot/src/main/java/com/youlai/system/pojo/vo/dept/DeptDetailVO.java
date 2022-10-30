package com.youlai.system.pojo.vo.dept;

import com.youlai.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("部门详情对象")
@Data
public class DeptDetailVO extends BaseEntity {

    @ApiModelProperty("部门ID(编辑必填)")
    private Long id;

    @ApiModelProperty("部门名称")
    private String name;

    @ApiModelProperty("父部门ID")
    private Long parentId;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("排序")
    private Integer sort;

}
