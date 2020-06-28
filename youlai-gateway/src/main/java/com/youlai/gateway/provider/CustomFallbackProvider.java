package com.youlai.gateway.provider;

import com.netflix.hystrix.exception.HystrixTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by XianRui on 2020-03-02 17:16
 **/
@Component
@Slf4j
public class CustomFallbackProvider implements FallbackProvider {

    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        HttpStatus status;
        if (cause instanceof HystrixTimeoutException) {
            status = HttpStatus.GATEWAY_TIMEOUT;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return status;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return status.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return status.getReasonPhrase();
            }

            @Override
            public void close() {
                log.info("close");
            }

            @Override
            public InputStream getBody() throws IOException {
                String message ="{\n" +
                        "\"code\": 200,\n" +
                        "\"message\": \"微服务故障, 请稍后再试\"\n" +
                        "}";

                return new ByteArrayInputStream(message.getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }
}
