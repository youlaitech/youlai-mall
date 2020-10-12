package com.youlai.admin.api.feign;

import com.youlai.admin.api.dto.UserDTO;
import com.youlai.common.core.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("youlai-admin")
public interface RemoteAdminService {

    @GetMapping("/users/user/{username}")
    Result<UserDTO> loadUserByUsername(@PathVariable String username);
}
