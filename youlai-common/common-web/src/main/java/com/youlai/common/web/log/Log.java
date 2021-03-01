package com.youlai.common.web.log;

import lombok.Data;

/**
 * @Author haoxr
 * @Date 2021-03-01 16:45
 * @Version 1.0.0
 */
@Data
public class Log {

    private String description;

    private String username;

    private String ip;

    private String url;

    private String method;

    private String gmtStart;

    private Integer intervalTime;

    private String result;

}
