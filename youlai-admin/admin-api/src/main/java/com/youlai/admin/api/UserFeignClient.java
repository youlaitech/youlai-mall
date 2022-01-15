package com.youlai.admin.api;

import com.youlai.admin.api.fallback.UserFeignFallbackClient;
import com.youlai.admin.dto.AuthUserDTO;
import com.youlai.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "youlai-admin", fallback = UserFeignFallbackClient.class)
public interface UserFeignClient {

    @GetMapping("/api/v1/users/username/{username}")
    Result<AuthUserDTO> getUserByUsername(@PathVariable String username);
}
