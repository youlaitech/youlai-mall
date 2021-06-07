package com.youlai.auth.jwt;

import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;

/**
 * KeyPairFactory
 **/
public class KeyPairFactory {

    public KeyPair getKeyPair(JwtProperties jwtProperties) {
        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(new ClassPathResource(jwtProperties.getKeyLocation()),
                jwtProperties.getKeyPass().toCharArray());
        KeyPair keyPair = factory.getKeyPair(jwtProperties.getKeyAlias(), jwtProperties.getKeyPass().toCharArray());
        return keyPair;
    }
}
