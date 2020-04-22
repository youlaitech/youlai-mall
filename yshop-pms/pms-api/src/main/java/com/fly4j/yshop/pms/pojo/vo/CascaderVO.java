package com.fly4j.yshop.pms.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel
public class CascaderVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 节点value */
    @ApiModelProperty(name="节点value")
    private String value;

    /** 节点名称 */
    @ApiModelProperty(name="节点label")
    private String label;

    /** 子节点 */
    @ApiModelProperty(name="子节点")
    private List<CascaderVO> children;
}
