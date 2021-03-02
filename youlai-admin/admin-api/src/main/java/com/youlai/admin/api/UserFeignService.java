package com.youlai.admin.api;

import com.youlai.admin.pojo.dto.UserDTO;
import com.youlai.common.result.Result;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "youlai-admin")
public interface UserFeignService {

    @GetMapping("/api.admin/v1/users/username/{username}")
    Result<UserDTO> getUserByUsername(@PathVariable String username);
}
