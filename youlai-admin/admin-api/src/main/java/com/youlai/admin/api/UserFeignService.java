package com.youlai.admin.api;

import com.youlai.admin.pojo.dto.UserDTO;
import com.youlai.common.core.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("youlai-admin")
public interface UserFeignService {

    @GetMapping("/users/username/{username}")
    Result<UserDTO> getUserByUsername(@PathVariable String username);
}
