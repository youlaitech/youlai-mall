package com.youlai.admin.api;

import com.youlai.admin.dto.UserDTO;
import com.youlai.common.core.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("youlai-admin")
public interface UserFeignService {

    @GetMapping("/users/{id}")
    Result<UserDTO> loadUserByUsername(@PathVariable Object id, @RequestParam Integer queryMode);
}
