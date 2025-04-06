package com.youlai.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.constant.RedisConstants;
import com.youlai.system.mapper.RoleMenuMapper;
import com.youlai.system.model.bo.RolePermsBO;
import com.youlai.system.model.entity.RoleMenu;
import com.youlai.system.service.RoleMenuService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 角色菜单服务实现类
 *
 * @author Ray.Hao
 * @since 2.5.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 初始化权限缓存
     */
    @PostConstruct
    public void initRolePermsCache() {
        log.info("初始化权限缓存... ");
        refreshRolePermsCache();
    }

    /**
     * 刷新权限缓存
     */
    @Override
    public void refreshRolePermsCache() {
        // 清理权限缓存
        redisTemplate.opsForHash().delete(RedisConstants.System.ROLE_PERMS, "*");

        List<RolePermsBO> list = this.baseMapper.getRolePermsList(null);
        if (CollectionUtil.isNotEmpty(list)) {
            list.forEach(item -> {
                String roleCode = item.getRoleCode();
                Set<String> perms = item.getPerms();
                if (CollectionUtil.isNotEmpty(perms)) {
                    redisTemplate.opsForHash().put(RedisConstants.System.ROLE_PERMS, roleCode, perms);
                }
            });
        }
    }

    /**
     * 刷新权限缓存
     */
    @Override
    public void refreshRolePermsCache(String roleCode) {
        // 清理权限缓存
        redisTemplate.opsForHash().delete(RedisConstants.System.ROLE_PERMS, roleCode);

        List<RolePermsBO> list = this.baseMapper.getRolePermsList(roleCode);
        if (CollectionUtil.isNotEmpty(list)) {
            RolePermsBO rolePerms = list.get(0);
            if (rolePerms == null) {
                return;
            }

            Set<String> perms = rolePerms.getPerms();
            if (CollectionUtil.isNotEmpty(perms)) {
                redisTemplate.opsForHash().put(RedisConstants.System.ROLE_PERMS, roleCode, perms);
            }
        }
    }

    /**
     * 刷新权限缓存 (角色编码变更时调用)
     */
    @Override
    public void refreshRolePermsCache(String oldRoleCode, String newRoleCode) {
        // 清理旧角色权限缓存
        redisTemplate.opsForHash().delete(RedisConstants.System.ROLE_PERMS, oldRoleCode);

        // 添加新角色权限缓存
        List<RolePermsBO> list = this.baseMapper.getRolePermsList(newRoleCode);
        if (CollectionUtil.isNotEmpty(list)) {
            RolePermsBO rolePerms = list.get(0);
            if (rolePerms == null) {
                return;
            }

            Set<String> perms = rolePerms.getPerms();
            redisTemplate.opsForHash().put(RedisConstants.System.ROLE_PERMS, newRoleCode, perms);
        }
    }

    /**
     * 获取角色权限集合
     *
     * @param roles 角色编码集合
     * @return 权限集合
     */
    @Override
    public Set<String> getRolePermsByRoleCodes(Set<String> roles) {
        return this.baseMapper.listRolePerms(roles);
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
