package com.fly.file.feign.fallback;

import com.fly.common.core.domain.Result;
import com.fly.file.feign.RemoteFileService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class RemoteFileServiceFallback implements FallbackFactory<RemoteFileService> {
    @Override
    public RemoteFileService create(Throwable throwable) {
        return new RemoteFileService() {
            @Override
            public Result upload(MultipartFile file) {
                return null;
            }

            @Override
            public Result deleteFile(String filePath) {
                return null;
            }
        };
    }
}
