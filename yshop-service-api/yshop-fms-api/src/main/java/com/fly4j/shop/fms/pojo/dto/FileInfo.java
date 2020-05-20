package com.fly4j.shop.fms.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by XianRui on 2020-03-04 11:08
 **/
@Data
@Accessors(chain = true)
@ApiModel(value = "文件传输实体")
public class FileInfo {

    @ApiModelProperty(name = "文件路径")
    private String url;

    @ApiModelProperty(name = "文件名称")
    private String name;

}
