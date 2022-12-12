package com.youlai.system.pojo.form;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("字典数据项")
@Data
public class DictItemForm {

    @ApiModelProperty("数据项ID(新增不填)")
    private Long id;

    @ApiModelProperty("类型编码")
    private String typeCode;

    @ApiModelProperty("数据项名称")
    private String name;

    @ApiModelProperty("值")
    private String value;

    @ApiModelProperty("状态：1->启用;0->禁用")
    private Integer status;

    @ApiModelProperty("排序")
    private Integer sort;

}
