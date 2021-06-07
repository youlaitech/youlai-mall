package com.youlai.auth.jwt;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.util.Assert;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Set;

/**
 * JwtTokenGenerator
 */
@Slf4j
public class JwtTokenGenerator {
    private static final String JWT_EXP_KEY = "exp";
    private KeyPair keyPair;
    private JwtPayloadBuilder jwtPayloadBuilder = new JwtPayloadBuilder();
    private JwtProperties jwtProperties;

    /**
     * Instantiates a new Jwt token generator.
     *
     * @param jwtProperties   the jwt properties
     */
    public JwtTokenGenerator(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;

        KeyPairFactory keyPairFactory = new KeyPairFactory();
        this.keyPair = keyPairFactory.getKeyPair(jwtProperties);
    }


    /**
     * Jwt token pair jwt token pair.
     *
     * @param aud         the aud
     * @param authorities the authorities
     * @param additional  the additional
     * @return the jwt token pair
     */
    public JwtTokenPair jwtTokenPair(String aud, Set<String> authorities, Map<String, String> additional) {
        String accessToken = jwtToken(aud, jwtProperties.getAccessExpDays(), authorities, additional);

        JwtTokenPair jwtTokenPair = new JwtTokenPair();
        jwtTokenPair.setToken_type("bearer");
        jwtTokenPair.setAccess_token(accessToken);
        return jwtTokenPair;
    }

    /**
     * Jwt token string.
     *
     * @param aud         the aud
     * @param exp         the exp
     * @param authorities the authorities
     * @param additional  the additional
     * @return the string
     */
    private String jwtToken(String aud, int exp, Set<String> authorities, Map<String, String> additional) {
        String payload = jwtPayloadBuilder
                .iss(jwtProperties.getIss())
                .sub(jwtProperties.getSub())
                .aud(aud)
                .additional(additional)
                .authorities(authorities)
                .expDays(exp)
                .builder();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        RsaSigner signer = new RsaSigner(privateKey);
        return JwtHelper.encode(payload, signer).getEncoded();
    }


    /**
     * 解码 并校验签名 过期不予解析
     *
     * @param jwtToken the jwt token
     * @return the jwt claims
     */
    public JSONObject decodeAndVerify(String jwtToken) {
        Assert.hasText(jwtToken, "jwt token must not be bank");
        RSAPublicKey rsaPublicKey = (RSAPublicKey) this.keyPair.getPublic();
        SignatureVerifier rsaVerifier = new RsaVerifier(rsaPublicKey);
        Jwt jwt = JwtHelper.decodeAndVerify(jwtToken, rsaVerifier);
        String claims = jwt.getClaims();
        JSONObject jsonObject = JSONUtil.parseObj(claims);
        String exp = jsonObject.getStr(JWT_EXP_KEY);

        if (isExpired(exp)) {
            throw new IllegalStateException("jwt token is expired");
        }
        return jsonObject;
    }

    /**
     * 判断jwt token是否过期.
     *
     * @param exp the jwt token exp
     * @return the boolean
     */
    private boolean isExpired(String exp) {
        return LocalDateTime.now().isAfter(LocalDateTime.ofEpochSecond(Long.parseLong(exp), 0, ZoneOffset.ofHours(8)));
    }
}
