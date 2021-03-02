package com.youlai.common.web.pojo.domain;

import lombok.Data;

/**
 * @Author haoxr
 * @Date 2021-03-01 16:45
 * @Version 1.0.0
 */
@Data
public class LoginLog {

    private String description;

    private String ip;

    private String url;

    private String method;

    private String gmtStart;

    /**
     * 消耗时间
     */
    private Integer elapsedTime;

}
