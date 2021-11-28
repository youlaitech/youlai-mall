package com.youlai.admin.component.cache;

import com.youlai.admin.service.ISysPermissionService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 容器启动完成时加载角色权限规则至Redis缓存
 * @author haoxr
 */
@Component
@AllArgsConstructor
public class InitPermissionRolesCache implements CommandLineRunner {

    private ISysPermissionService iSysPermissionService;

    @Override
    public void run(String... args) {
        iSysPermissionService.refreshPermRolesRules();
    }
}
