package com.fly.system.feign.fallback;

import com.fly.system.feign.IFileService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileServiceFallback implements FallbackFactory<IFileService> {

    @Override
    public IFileService create(Throwable throwable) {
        return new IFileService() {
            @Override
            public String upload(MultipartFile file) {
                return null;
            }

            @Override
            public void deleteFile(String fireUrl) {
            }
        };
    }
}
