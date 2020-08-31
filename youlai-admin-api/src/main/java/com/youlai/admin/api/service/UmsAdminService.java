package com.youlai.admin.api.service;

import com.youlai.admin.api.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("youlai-admin")
public interface UmsAdminService {

    @GetMapping("/users/loadUserByUsername")
    UserDTO loadUserByUsername(@RequestParam String username);

}
