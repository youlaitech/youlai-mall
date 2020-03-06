package com.fly.file.feign;

import com.fly.common.constant.ServiceNameConstants;
import com.fly.common.core.domain.Result;
import com.fly.file.feign.fallback.RemoteFileServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * RemoteFileService
 *
 * @author haoxianrui
 * @version 1.0
 * @date 2020-03-06 19:41
 **/

@FeignClient(name = "fly-file-provider", contextId ="file",fallbackFactory = RemoteFileServiceFallback.class)
public interface RemoteFileService {
    @PostMapping(value = "/files",produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Result upload(@RequestPart("file") MultipartFile file);

    @DeleteMapping("/files")
    Result deleteFile(@RequestParam String filePath);

}
