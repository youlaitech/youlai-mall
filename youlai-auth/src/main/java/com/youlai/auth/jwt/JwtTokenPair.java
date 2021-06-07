package com.youlai.auth.jwt;

import lombok.Data;

import java.io.Serializable;

/**
 * JwtTokenPair
 *
 **/
@Data
public class JwtTokenPair implements Serializable {
    private static final long serialVersionUID = -8518897818107784049L;
    private String access_token;
    private String token_type;
}
