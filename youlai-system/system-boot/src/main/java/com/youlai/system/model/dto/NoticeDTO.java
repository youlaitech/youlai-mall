package com.youlai.system.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 通知传送对象
 *
 * @author Theo
 * @since 2024-9-2 14:32:58
 */
@Data
public class NoticeDTO {

    @Schema(description = "通知ID")
    private Long id;

    @Schema(description = "通知类型")
    private Integer type;

    @Schema(description = "通知标题")
    private String title;

    @Schema(description = "通知时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime publishTime;


}
