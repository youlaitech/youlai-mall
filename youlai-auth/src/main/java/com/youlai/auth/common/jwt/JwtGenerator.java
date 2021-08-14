package com.youlai.auth.common.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.Map;
import java.util.Set;

/**
 * 描述: [类型描述]
 * 创建时间: 2021-06-08
 *
 * @author hxr
 * @version 1.0.0
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Component
public class JwtGenerator {


    @Autowired
    private KeyPair keyPair;

    public String createAccessToken(Set<String> authorities, Map<String, String> additional) {
        String payload = new JwtPayloadBuilder()
                .exp(12 * 3600) // 默认12小时
                .authorities(authorities)
                .additional(additional)
                .builder();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RsaSigner signer = new RsaSigner(privateKey);
        String accessToken = JwtHelper.encode(payload, signer).getEncoded();
        return accessToken;
    }


}



