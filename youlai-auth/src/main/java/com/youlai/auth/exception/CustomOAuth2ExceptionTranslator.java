package com.youlai.auth.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

import javax.naming.AuthenticationException;

public class CustomOAuth2ExceptionTranslator implements WebResponseExceptionTranslator {
    @Override
    public ResponseEntity translate(Exception exception) throws Exception {
        if (exception instanceof OAuth2Exception) {

        }else if(exception instanceof AuthenticationException){

        }
        return null;
    }
}
