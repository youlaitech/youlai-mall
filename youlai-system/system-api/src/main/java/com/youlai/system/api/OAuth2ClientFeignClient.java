package com.youlai.system.api;

import com.youlai.common.result.Result;
import com.youlai.system.dto.OAuth2ClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient(value = "youlai-system", contextId = "oauth-client")
public interface OAuth2ClientFeignClient {
    @GetMapping("/api/v1/oauth-clients/getOAuth2ClientById")
    Result<OAuth2ClientDTO> getOAuth2ClientById(@RequestParam String clientId);

}
