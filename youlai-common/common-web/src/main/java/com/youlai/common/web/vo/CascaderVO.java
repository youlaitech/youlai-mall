package com.youlai.common.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel
public class CascaderVO {

    @ApiModelProperty(name="节点value")
    private String value;

    @ApiModelProperty(name="节点label")
    private String label;

    @ApiModelProperty(name="子节点")
    private List<CascaderVO> children;
}
