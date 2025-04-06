package com.youlai.system.api;

import com.youlai.common.core.config.FeignDecoderConfig;
import com.youlai.system.api.fallback.UserFeignFallbackClient;
import com.youlai.system.dto.UserAuthCredentials;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "youlai-system", contextId = "user", fallback = UserFeignFallbackClient.class, configuration = {FeignDecoderConfig.class})
public interface UserFeignClient {

    @GetMapping("/api/v1/users/{username}/authInfo")
    UserAuthCredentials getUserAuthInfo(@PathVariable String username);
}
