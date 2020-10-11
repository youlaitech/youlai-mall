package com.youlai.auth.domain;

import lombok.Data;

@Data
public class WxLoginInfo {

    private String code;

    private String encryptedData;

    private String iv;



}
