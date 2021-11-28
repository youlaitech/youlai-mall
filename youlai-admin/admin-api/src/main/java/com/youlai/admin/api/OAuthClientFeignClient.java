package com.youlai.admin.api;

import com.youlai.admin.dto.OAuth2ClientDTO;
import com.youlai.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "youlai-admin", contextId = "oauth-client")
public interface OAuthClientFeignClient {

    @GetMapping("/api/v1/oauth-clients/getOAuth2ClientById")
    Result<OAuth2ClientDTO> getOAuth2ClientById(@RequestParam String clientId);
}
