package com.youlai.admin.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.admin.mapper.SysRolePermissionMapper;
import com.youlai.admin.pojo.entity.SysRolePermission;
import com.youlai.admin.pojo.form.RolePermsForm;
import com.youlai.admin.service.ISysRolePermissionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色权限实现类
 */
@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements ISysRolePermissionService {


    /**
     * 根据菜单ID和角色ID获取权限ID集合
     *
     * @param menuId
     * @param roleId
     * @return
     */
    @Override
    public List<Long> listPermIds(Long menuId, Long roleId) {
        return this.baseMapper.listPermIds(menuId, roleId);
    }

    /**
     * 保存角色权限
     *
     * @return
     */
    @Override
    public boolean saveRolePerms(RolePermsForm rolePermsForm) {

        Long menuId = rolePermsForm.getMenuId();
        Long roleId = rolePermsForm.getRoleId();
        List<Long> permIds = rolePermsForm.getPermIds();

        List<Long> oldPermIds = this.listPermIds(menuId, roleId);

        // 验证权限数据是否改变
        List<Long> sortedPermIds = permIds.stream().sorted().collect(Collectors.toList());
        List<Long> sortedOldPermIds = oldPermIds.stream().sorted().collect(Collectors.toList());
        boolean permDataChangeFlag = !CollectionUtil.isEqualList(sortedPermIds, sortedOldPermIds);
        Assert.isTrue(permDataChangeFlag, "提交失败，权限数据无改动！");

        // 删除此次保存移除的权限
        boolean updateFlag = false;
        if (CollectionUtil.isNotEmpty(oldPermIds)) {
            List<Long> removePermIds = oldPermIds.stream().filter(id -> !permIds.contains(id)).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(removePermIds)) {
                updateFlag = this.remove(new LambdaQueryWrapper<SysRolePermission>()
                        .eq(SysRolePermission::getRoleId, roleId)
                        .in(SysRolePermission::getPermissionId, removePermIds));
            }
        }

        // 新增数据库不存在的权限
        if (CollectionUtil.isNotEmpty(permIds)) {
            List<Long> newPermIds = permIds.stream().filter(id -> !oldPermIds.contains(id)).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(newPermIds)) {
                List<SysRolePermission> rolePerms = new ArrayList<>();
                for (Long permId : newPermIds) {
                    SysRolePermission rolePerm = new SysRolePermission(roleId, permId);
                    rolePerms.add(rolePerm);
                }
                updateFlag = this.saveBatch(rolePerms);
            }
        }
        return updateFlag;
    }


}
