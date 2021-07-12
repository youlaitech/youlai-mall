package com.youlai.common.pojo;

import lombok.Data;

/**
 * JWT载体
 *
 * @author hxr
 * @date 2021-03-10
 */
@Data
public class JwtPayload {

    private String jti;

    private Long exp;
}
