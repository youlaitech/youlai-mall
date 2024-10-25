package com.youlai.system.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 阅读通知公告VO
 *
 * @author Theo
 * @since 2024-9-8 01:25:06
 */
@Data
public class NoticeDetailVO {

    @Schema(description = "通知ID")
    private Long id;

    @Schema(description = "通知标题")
    private String title;

    @Schema(description = "通知内容")
    private String content;

    @Schema(description = "通知类型")
    private Integer type;

    @Schema(description = "发布人")
    private String publisherName;

    @Schema(description = "优先级(L-低 M-中 H-高)")
    private String level;

    @Schema(description = "发布状态(0-未发布 1已发布 2已撤回) 冗余字段，方便判断是否已经发布")
    private Integer publishStatus;

    @Schema(description = "发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishTime;
}
