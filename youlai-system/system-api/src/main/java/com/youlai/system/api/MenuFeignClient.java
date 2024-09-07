package com.youlai.system.api;

import com.youlai.common.core.config.FeignDecoderConfig;
import com.youlai.system.dto.CodegenMenuDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "youlai-system", contextId = "menu", configuration = {FeignDecoderConfig.class})
public interface MenuFeignClient {

    @PostMapping("/api/v1/menus/codegen")
    void addMenuForCodegen( @RequestBody CodegenMenuDTO data);
}
