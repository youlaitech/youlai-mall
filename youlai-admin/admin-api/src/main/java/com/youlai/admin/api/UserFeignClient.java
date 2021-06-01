package com.youlai.admin.api;

import com.youlai.admin.api.fallback.UserFeignFallbackClient;
import com.youlai.admin.pojo.dto.UserDTO;
import com.youlai.admin.pojo.entity.SysUser;
import com.youlai.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "youlai-admin", fallback = UserFeignFallbackClient.class)
public interface UserFeignClient {

    @GetMapping("/users/username/{username}")
    Result<SysUser> getUserByUsername(@PathVariable String username);
}
