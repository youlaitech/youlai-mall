package com.youlai.system.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.youlai.common.enums.LogModuleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统日志分页VO
 *
 * @author Ray
 * @since 2.10.0
 */
@Data
@Schema(description = "系统日志分页VO")
public class LogPageVO implements Serializable {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "日志模块")
    private LogModuleEnum module;

    @Schema(description = "日志内容")
    private String content;

    @Schema(description = "请求路径")
    private String requestUri;

    @Schema(description = "请求方法")
    private String method;

    @Schema(description = "IP 地址")
    private String ip;

    @Schema(description = "地区")
    private String region;

    @Schema(description = "浏览器")
    private String browser;

    @Schema(description = "终端系统")
    private String os;

    @Schema(description = "执行时间(毫秒)")
    private Long executionTime;

    @Schema(description = "创建人ID")
    private Long createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "操作人")
    private String operator;
}