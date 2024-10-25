package com.youlai.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 通知公告表单对象
 *
 * @author youlaitech
 * @since 2024-08-27 10:31
 */
@Getter
@Setter
@Schema(description = "通知公告表单对象")
public class NoticeForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "通知ID")
    private Long id;

    @Schema(description = "通知标题")
    @NotBlank(message = "通知标题不能为空")
    @Size(max=50, message="通知标题长度不能超过50个字符")
    private String title;

    @Schema(description = "通知内容")
    @NotBlank(message = "通知内容不能为空")
    @Size(max=65535, message="通知内容长度不能超过65535个字符")
    private String content;

    @Schema(description = "通知类型")
    private Integer type;

    @Schema(description = "优先级(L-低 M-中 H-高)")
    private String level;

    @Schema(description = "目标类型(1-全体 2-指定)")
    @Range(min = 1, max = 2, message = "目标类型取值范围[1,2]")
    private Integer targetType;

    @Schema(description = "接收人ID集合")
    private List<String> targetUserIds;

}
