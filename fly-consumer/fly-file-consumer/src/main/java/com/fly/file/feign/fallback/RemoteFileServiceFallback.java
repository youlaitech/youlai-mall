package com.fly.file.feign.fallback;

import com.baomidou.mybatisplus.extension.api.R;
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
            public R upload(MultipartFile file) {
                return null;
            }

            @Override
            public R deleteFile(String filePath) {
                return null;
            }

            @Override
            public R uploadImage(MultipartFile file) {
                return null;
            }

            @Override
            public R uploadImageWithThumb(MultipartFile file) {
                return null;
            }
        };
    }
}
