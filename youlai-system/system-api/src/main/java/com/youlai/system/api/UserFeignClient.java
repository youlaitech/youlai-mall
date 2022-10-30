package com.youlai.system.api;

import com.youlai.system.api.fallback.UserFeignFallbackClient;
import com.youlai.system.dto.UserAuthInfo;
import com.youlai.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "youlai-system", fallback = UserFeignFallbackClient.class)
public interface UserFeignClient {

    @GetMapping("/api/v1/users/{username}/authinfo")
    Result<UserAuthInfo> getUserAuthInfo(@PathVariable String username);
}
