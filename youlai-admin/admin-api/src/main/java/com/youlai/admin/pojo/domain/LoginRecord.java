package com.youlai.admin.pojo.domain;

import lombok.Data;

/**
 * @author hxr
 * @date 2021-03-09
 */
@Data
public class LoginRecord {

    private String _id;

    private String description;

    private String clientIP;

    private long elapsedTime;

    private Object message;

    private String token;

    private String username;

    private String loginTime;

    private String region;

}
