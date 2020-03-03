package com.fly.system.feign;

import com.fly.common.constant.ServiceNameConstants;
import com.fly.system.config.FeignMultipartSupportConfig;
import com.fly.system.feign.fallback.FileServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * IFileService
 *
 * @author haoxianrui
 * @version 1.0
 * @date 2020-03-03 18:36
 **/
@FeignClient(name = ServiceNameConstants.SYSTEM_PROVIDER, contextId ="file",configuration = FeignMultipartSupportConfig.class,fallbackFactory = FileServiceFallback.class)
public interface IFileService {

    @PostMapping(value = "/files",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String upload(@RequestPart("file") MultipartFile file);

    @DeleteMapping("/files/fileUrl/{fileUrl}")
    void deleteFile(@PathVariable String fileUrl);

}
