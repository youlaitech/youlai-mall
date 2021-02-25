package com.youlai.admin.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.admin.pojo.entity.SysUser;
import com.youlai.admin.mapper.SysUserMapper;
import com.youlai.admin.pojo.entity.SysUserRole;
import com.youlai.admin.service.ISysUserRoleService;
import com.youlai.admin.service.ISysUserService;
import com.youlai.common.constant.GlobalConstants;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {


    private final PasswordEncoder passwordEncoder;

    private final ISysUserRoleService iSysUserRoleService;

    @Override
    public IPage<SysUser> list(Page<SysUser> page, SysUser user) {
        List<SysUser> list = this.baseMapper.list(page, user);
        page.setRecords(list);
        return page;
    }

    @Override
    public boolean saveUser(SysUser user) {
        user.setPassword(passwordEncoder.encode(GlobalConstants.DEFAULT_USER_PASSWORD));
        boolean result = this.save(user);
        if (result) {
            List<Long> roleIds = user.getRoleIds();
            if (CollectionUtil.isNotEmpty(roleIds)) {
                List<SysUserRole> userRoleList = new ArrayList<>();
                roleIds.forEach(roleId -> userRoleList.add(new SysUserRole().setUserId(user.getId()).setRoleId(roleId)));
                result = iSysUserRoleService.saveBatch(userRoleList);
            }
        }
        return result;
    }

    @Override
    public boolean updateUser(SysUser user) {

        List<Long> dbRoleIds = iSysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, user.getId())).stream().map(item -> item.getRoleId()).collect(Collectors.toList());

        List<Long> roleIds = user.getRoleIds();

        List<Long> addRoleIds = roleIds.stream().filter(roleId -> !dbRoleIds.contains(roleId)).collect(Collectors.toList());
        List<Long> removeRoleIds = dbRoleIds.stream().filter(roleId -> !roleIds.contains(roleId)).collect(Collectors.toList());

        if (CollectionUtil.isNotEmpty(addRoleIds)) {
            List<SysUserRole> addUserRoleList = new ArrayList<>();
            addRoleIds.forEach(roleId -> {
                addUserRoleList.add(new SysUserRole().setUserId(user.getId()).setRoleId(roleId));
            });
            iSysUserRoleService.saveBatch(addUserRoleList);
        }

        if (CollectionUtil.isNotEmpty(removeRoleIds)) {
            removeRoleIds.forEach(roleId -> {
                iSysUserRoleService.remove(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, user.getId()).eq(SysUserRole::getRoleId, roleId));
            });
        }

        boolean result = this.updateById(user);
        return result;
    }


}
