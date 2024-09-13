package com.youlai.system.model.bo;

import lombok.Data;

/**
 * 特定日期访问统计
 *
 * @author Ray
 * @since 2.10.0
 */
@Data
public class VisitCount {

    /**
     * 日期 yyyy-MM-dd
     */
    private String date;

    /**
     * 访问次数
     */
    private Integer count;
}
