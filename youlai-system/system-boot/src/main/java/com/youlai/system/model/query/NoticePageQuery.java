package com.youlai.system.model.query;

import com.youlai.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 通知公告分页查询对象
 *
 * @author youlaitech
 * @since 2024-08-27 10:31
 */
@Schema(description ="通知公告查询对象")
@EqualsAndHashCode(callSuper = false)
@Data
public class NoticePageQuery extends BasePageQuery {

    @Schema(description = "通知标题")
    private String title;

    @Schema(description = "发布状态(0-未发布 1已发布 -1已撤回)")
    private Integer publishStatus;

    @Schema(description = "发布时间(起止)")
    private List<String> publishTime;

    @Schema(description = "查询人ID")
    private Long userId;

    @Schema(description = "是否已读（0-未读 1-已读）")
    private Integer isRead;

}
