package com.youlai.common.web.pojo.domain;

import lombok.Data;

import java.util.Date;

/**
 * @Author haoxr
 * @Date 2021-03-01 16:45
 * @Version 1.0.0
 */
@Data
public class OptLog {

    private String description;

    private String requestIp;

    private String requestUrl;

    private String requestMethod;

    private long startTime;

    private long elapsedTime;

    private Object result;

}
