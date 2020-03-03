package com.fly.system.feign;

import com.fly.common.constant.ServiceNameConstants;
import com.fly.system.domain.SysUser;
import com.fly.system.feign.fallback.FileServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * IFileService
 *
 * @author haoxianrui
 * @version 1.0
 * @date 2020-03-03 18:36
 **/
@FeignClient(name = ServiceNameConstants.SYSTEM_PROVIDER, contextId ="file",fallbackFactory = FileServiceFallback.class)
public interface IFileService {

    @PostMapping("/files")
    String upload(@RequestParam("file") MultipartFile file);

    @DeleteMapping("/files/fireUrl/{fireUrl}")
    void deleteFile(@PathVariable String fireUrl);

}
