package com.youlai.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.constant.RedisConstants;
import com.youlai.system.mapper.SysRoleMenuMapper;
import com.youlai.system.model.bo.RolePermsBO;
import com.youlai.system.model.entity.SysRoleMenu;
import com.youlai.system.service.SysRoleMenuService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 角色菜单业务实现类
 */
@Service
@RequiredArgsConstructor
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {


    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 初始化权限缓存
     */
    @PostConstruct
    public void initRolePermsCache() {
        refreshRolePermsCache();
    }

    /**
     * 刷新权限缓存
     */
    @Override
    public void refreshRolePermsCache() {
        // 清理权限缓存
        redisTemplate.opsForHash().delete(RedisConstants.ROLE_PERMS_PREFIX, "*");

        List<RolePermsBO> list = this.baseMapper.getRolePermsList(null);
        if (CollectionUtil.isNotEmpty(list)) {
            list.forEach(item -> {
                String roleCode = item.getRoleCode();
                Set<String> perms = item.getPerms();
                redisTemplate.opsForHash().put(RedisConstants.ROLE_PERMS_PREFIX, roleCode, perms);
            });
        }
    }

    /**
     * 刷新权限缓存
     */
    @Override
    public void refreshRolePermsCache(String roleCode) {
        // 清理权限缓存
        redisTemplate.opsForHash().delete(RedisConstants.ROLE_PERMS_PREFIX, roleCode);

        List<RolePermsBO> list = this.baseMapper.getRolePermsList(roleCode);
        if (CollectionUtil.isNotEmpty(list)) {
            RolePermsBO rolePerms = list.get(0);
            if (rolePerms == null) {
                return;
            }

            Set<String> perms = rolePerms.getPerms();
            redisTemplate.opsForHash().put(RedisConstants.ROLE_PERMS_PREFIX, roleCode, perms);
        }
    }

    /**
     * 刷新权限缓存 (角色编码变更时调用)
     */
    @Override
    public void refreshRolePermsCache(String oldRoleCode, String newRoleCode) {
        // 清理旧角色权限缓存
        redisTemplate.opsForHash().delete(RedisConstants.ROLE_PERMS_PREFIX, oldRoleCode);

        // 添加新角色权限缓存
        List<RolePermsBO> list =this.baseMapper.getRolePermsList(newRoleCode);
        if (CollectionUtil.isNotEmpty(list)) {
            RolePermsBO rolePerms = list.get(0);
            if (rolePerms == null) {
                return;
            }

            Set<String> perms = rolePerms.getPerms();
            redisTemplate.opsForHash().put(RedisConstants.ROLE_PERMS_PREFIX, newRoleCode, perms);
        }
    }




    /**
     * 获取角色拥有的菜单ID集合
     *
     * @param roleId 角色ID
     * @return 菜单ID集合
     */
    @Override
    public List<Long> listMenuIdsByRoleId(Long roleId) {
        return this.baseMapper.listMenuIdsByRoleId(roleId);
    }

}
