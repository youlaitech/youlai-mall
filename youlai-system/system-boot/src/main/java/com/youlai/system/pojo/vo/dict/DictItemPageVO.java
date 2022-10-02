package com.youlai.system.pojo.vo.dict;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("字典数据项分页对象")
@Data
public class DictItemPageVO {

    @ApiModelProperty("数据项ID")
    private Long id;

    @ApiModelProperty("数据项名称")
    private String name;

    @ApiModelProperty("值")
    private String value;

    @ApiModelProperty("类型状态：1->启用;0->禁用")
    private Integer status;

}
