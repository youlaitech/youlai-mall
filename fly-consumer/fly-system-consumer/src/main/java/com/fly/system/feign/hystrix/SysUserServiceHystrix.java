package com.fly.system.feign.hystrix;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fly.common.core.domain.Result;
import com.fly.system.feign.ISysUserService;
import com.fly.system.domain.SysUser;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class SysUserServiceHystrix implements FallbackFactory<ISysUserService> {

    @Override
    public ISysUserService create(Throwable throwable) {
        return new ISysUserService() {
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
