package com.fly.cloud.admin.api.service.fallback;

import com.fly.cloud.admin.api.service.ISysUserService;
import com.fly.common.pojo.entity.SysUser;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class SysUserServiceFallbackFactory implements FallbackFactory<ISysUserService> {
    @Override
    public ISysUserService create(Throwable throwable) {
        return new ISysUserService() {
            @Override
            public SysUser get(Long id) {
                return new SysUser().setId(null)
                        .setNickName("无此人信息");
            }
        };
    }
}
