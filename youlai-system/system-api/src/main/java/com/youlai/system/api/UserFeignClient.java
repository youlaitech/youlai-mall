package com.youlai.system.api;

import com.youlai.common.web.config.FeignDecoderConfig;
import com.youlai.system.api.fallback.UserFeignFallbackClient;
import com.youlai.system.dto.UserAuthInfo;
import com.youlai.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "youlai-system", fallback = UserFeignFallbackClient.class, configuration = {FeignDecoderConfig.class})
public interface UserFeignClient {

    @GetMapping("/api/v1/users/{username}/authInfo")
    UserAuthInfo getUserAuthInfo(@PathVariable String username);
}
