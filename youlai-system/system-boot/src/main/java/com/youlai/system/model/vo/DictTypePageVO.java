package com.youlai.system.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description ="字典类型分页对象")
@Data
public class DictTypePageVO {

    @Schema(description="字典类型ID")
    private Long id;

    @Schema(description="类型名称")
    private String name;

    @Schema(description="类型编码")
    private String code;

    @Schema(description="状态：1:启用;0:禁用")
    private Integer status;

}
