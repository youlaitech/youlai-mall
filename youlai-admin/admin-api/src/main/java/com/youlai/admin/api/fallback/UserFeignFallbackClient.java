package com.youlai.admin.api.fallback;

import com.youlai.admin.api.UserFeignClient;
import com.youlai.admin.pojo.dto.UserDTO;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author haoxr
 * @createTime 2021/4/24 21:30
 */
@Component
@Slf4j
public class UserFeignFallbackClient implements UserFeignClient {

    @Override
    public Result<UserDTO> getUserByUsername(String username) {
        log.error("Feign远程调用服务发生故障，获取用户信息失败降级");
        return Result.failed(ResultCode.DEGRADATION);
    }
}
