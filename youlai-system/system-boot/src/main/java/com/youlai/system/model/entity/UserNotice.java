package com.youlai.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 用户通知公告实体对象
 *
 * @author Kylin
 * @since 2024-08-28 16:56
 */
@Getter
@Setter
@TableName("sys_user_notice")
public class UserNotice extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 公共通知id
     */
    private Long noticeId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 读取状态，0未读，1已读
     */
    private Integer isRead;
    /**
     * 用户阅读时间
     */
    private LocalDateTime readTime;

    /**
     * 逻辑删除标识(0-未删除 1-已删除)
     */
    @TableLogic(value = "0", delval = "1")
    private Integer isDeleted;
}
