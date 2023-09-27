package com.youlai.system.api.fallback;

import com.youlai.system.api.UserFeignClient;
import com.youlai.system.dto.UserAuthInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 系统用户服务远程调用异常后的降级处理类
 *
 * @author haoxr
 * @since 2021/4/24
 */
@Component
@Slf4j
public class UserFeignFallbackClient implements UserFeignClient {

    @Override
    public UserAuthInfo getUserAuthInfo(String username) {
        log.error("feign远程调用系统用户服务异常后的降级方法");
        return new UserAuthInfo();
    }
}
