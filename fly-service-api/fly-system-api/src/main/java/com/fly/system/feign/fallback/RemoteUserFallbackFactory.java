package com.fly.system.feign.fallback;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fly.system.feign.RemoteUserService;
import com.fly.system.pojo.dto.Result;
import com.fly.system.pojo.entity.SysUser;
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
                return Result.success("用户不存在");
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
