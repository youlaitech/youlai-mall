package com.youlai.admin.api;

import com.youlai.admin.pojo.entity.SysOauthClient;
import com.youlai.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "youlai-admin", contextId = "oauth-client")
public interface OAuthClientFeignClient {

    @GetMapping("/api/v1/oauth-clients/{clientId}")
    Result<SysOauthClient> getOAuthClientById(@PathVariable String clientId);
}
