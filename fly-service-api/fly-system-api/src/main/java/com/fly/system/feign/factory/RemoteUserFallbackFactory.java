package com.fly.system.feign.factory;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fly.common.core.domain.Result;
import com.fly.system.feign.RemoteUserService;
import com.fly.system.entity.SysUser;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteUserService> {

    @Override
    public RemoteUserService create(Throwable throwable) {
        return new RemoteUserService() {
            @Override
            public Result<IPage<SysUser>> page() {
                return null;
            }

            @Override
            public Result<SysUser> getById(Long id) {
                return null;
            }

            @Override
            public Result<SysUser> add(SysUser sysUser) {
                return null;
            }

            @Override
            public Result<SysUser> update(Long id, SysUser sysUser) {
                return null;
            }

            @Override
            public Result<Boolean> delete(Long[] ids) {
                return null;
            }
        };
    }
}
