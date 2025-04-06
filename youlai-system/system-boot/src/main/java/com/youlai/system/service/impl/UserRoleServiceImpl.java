package com.youlai.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.system.mapper.UserRoleMapper;
import com.youlai.system.model.entity.UserRole;
import com.youlai.system.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    /**
     * 保存用户角色
     *
     * @param userId
     * @param roleIds
     * @return
     */
    @Override
    public boolean saveUserRoles(Long userId, List<Long> roleIds) {

        if (userId == null || CollectionUtil.isEmpty(roleIds)) {
            return false;
        }

        // 用户原角色ID集合
        List<Long> userRoleIds = this.list(new LambdaQueryWrapper<UserRole>()
                        .eq(UserRole::getUserId, userId))
                .stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());

        // 新增用户角色
        List<Long> saveRoleIds;
        if (CollectionUtil.isEmpty(userRoleIds)) {
            saveRoleIds = roleIds;
        } else {
            saveRoleIds = roleIds.stream()
                    .filter(roleId -> !userRoleIds.contains(roleId))
                    .collect(Collectors.toList());
        }

        List<UserRole> saveUserRoles = saveRoleIds
                .stream()
                .map(roleId -> new UserRole(userId, roleId))
                .collect(Collectors.toList());
        this.saveBatch(saveUserRoles);

        // 删除用户角色
        if (CollectionUtil.isNotEmpty(userRoleIds)) {
            List<Long> removeRoleIds = userRoleIds.stream()
                    .filter(roleId -> !roleIds.contains(roleId))
                    .collect(Collectors.toList());

            if (CollectionUtil.isNotEmpty(removeRoleIds)) {
                this.remove(new LambdaQueryWrapper<UserRole>()
                        .eq(UserRole::getUserId, userId)
                        .in(UserRole::getRoleId, removeRoleIds)
                );
            }
        }
        return true;

    }

    /**
     * 判断角色是否存在绑定的用户
     *
     * @param roleId 角色ID
     * @return true：已分配 false：未分配
     */
    @Override
    public boolean hasAssignedUsers(Long roleId) {
        int count = this.baseMapper.countUsersForRole(roleId);
        return count > 0;
    }
}
