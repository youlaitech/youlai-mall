package com.youlai.system.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description ="字典分页对象")
@Data
public class DictPageVO {

    @Schema(description="字典ID")
    private Long id;

    @Schema(description="字典名称")
    private String name;

    @Schema(description="字典值")
    private String value;

    @Schema(description="状态(1:启用;0:禁用)")
    private Integer status;

}
