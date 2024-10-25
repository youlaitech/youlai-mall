package com.youlai.system.model.bo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 通知公告业务对象
 *
 * @author Theo
 * @since 2024-09-01 10:31
 */
@Data
public class NoticeBO {

    /**
     * 通知ID
     */
    private Long id;

    /**
     * 通知标题
     */
    private String title;

    /**
     * 通知类型
     */
    private Integer type;

    /**
     * 通知类型标签
     */
    private String typeLabel;

    /**
     * 通知内容
     */
    private String content;

    /**
     * 发布人姓名
     */
    private String publisherName;

    /**
     * 通知等级（L: 低, M: 中, H: 高）
     */
    private String level;

    /**
     * 目标类型(1: 全体 2: 指定)
     */
    private Integer targetType;

    /**
     * 发布状态（0: 未发布, 1: 已发布, -1: 已撤回）
     */
    private Integer publishStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;

    /**
     * 撤回时间
     */
    private LocalDateTime revokeTime;
}
