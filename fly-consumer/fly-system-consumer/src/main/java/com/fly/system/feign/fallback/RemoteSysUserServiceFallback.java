package com.fly.system.feign.fallback;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly.common.core.domain.Result;
import com.fly.system.feign.RemoteSysUserService;
import com.fly.system.domain.SysUser;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteSysUserServiceFallback implements FallbackFactory<RemoteSysUserService> {

    @Override
    public RemoteSysUserService create(Throwable throwable) {
        return new RemoteSysUserService() {

            @Override
            public Result<Page<SysUser>> page(Integer pageNum, Integer pageSize, SysUser sysUser) {
                return null;
            }

            @Override
            public Result<SysUser> getById(Long id) {
                return null;
            }

            @Override
            public Result add(SysUser sysUser) {
                return null;
            }

            @Override
            public Result update(Long id, SysUser sysUser) {
                return null;
            }

            @Override
            public Result delete(Long[] ids) {
                return null;
            }
        };
    }
}
