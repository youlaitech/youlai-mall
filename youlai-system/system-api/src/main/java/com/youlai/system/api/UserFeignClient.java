package com.youlai.system.api;

import com.youlai.system.api.fallback.UserFeignFallbackClient;
import com.youlai.common.result.Result;
import com.youlai.common.security.dto.UserAuthDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "youlai-system", fallback = UserFeignFallbackClient.class)
public interface UserFeignClient {

    @GetMapping("/api/v1/users/username/{username}")
    Result<UserAuthDTO> getUserByUsername(@PathVariable String username);

}