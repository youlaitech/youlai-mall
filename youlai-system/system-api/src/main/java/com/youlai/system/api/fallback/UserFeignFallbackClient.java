package com.youlai.system.api.fallback;

import com.youlai.system.api.UserFeignClient;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 用户调用回调
 *
 * @author haoxr
 * @date 2021/4/24
 */
@Component
@Slf4j
public class UserFeignFallbackClient implements UserFeignClient {

    @Override
    public Result getUserAuthInfo(String username) {
        log.error("feign远程调用系统用户服务异常后的降级方法");
        return Result.failed(ResultCode.DEGRADATION);
    }
}
