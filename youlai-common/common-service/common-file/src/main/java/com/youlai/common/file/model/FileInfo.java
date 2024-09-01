package com.youlai.common.file.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 * 文件对象
 *
 * @author ray
 * @since 1.0.0
 */
@Schema(description = "文件对象")
@Data
public class FileInfo {

    @Schema(description = "文件名称")
    private String name;

    @Schema(description = "文件URL")
    private String url;

}
