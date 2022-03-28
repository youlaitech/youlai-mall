package com.youlai.admin.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.admin.constant.SystemConstants;
import com.youlai.admin.dto.AuthUserDTO;
import com.youlai.admin.pojo.entity.SysUser;
import com.youlai.admin.mapper.SysUserMapper;
import com.youlai.admin.pojo.entity.SysUserRole;
import com.youlai.admin.pojo.query.UserPageQuery;
import com.youlai.admin.pojo.vo.user.UserFormVO;
import com.youlai.admin.pojo.vo.user.UserPageVO;
import com.youlai.admin.service.ISysUserRoleService;
import com.youlai.admin.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户业务实现类
 *
 * @author haoxr
 * @date 2022/1/14
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    private final PasswordEncoder passwordEncoder;
    private final ISysUserRoleService iSysUserRoleService;

    /**
     * 获取用户分页列表
     *
     * @param queryParams
     * @return
     */
    @Override
    public IPage<UserPageVO> listUsersPage(UserPageQuery queryParams) {
        Page<UserPageVO> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        List<UserPageVO> list = this.baseMapper.listUsersPage(page, queryParams);
        page.setRecords(list);
        return page;
    }

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @Override
    public boolean saveUser(SysUser user) {
        user.setPassword(passwordEncoder.encode(SystemConstants.DEFAULT_USER_PASSWORD));
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

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    @Override
    public boolean updateUser(SysUser user) {

        // 原来的用户角色ID集合
        List<Long> oldRoleIds = iSysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId, user.getId())).stream()
                .map(item -> item.getRoleId())
                .collect(Collectors.toList());

        // 新的用户角色ID集合
        List<Long> newRoleIds = user.getRoleIds();

        // 需要新增的用户角色ID集合
        List<Long> addRoleIds = newRoleIds.stream().filter(roleId -> !oldRoleIds.contains(roleId)).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(addRoleIds)) {
            List<SysUserRole> addUserRoleList = new ArrayList<>();
            addRoleIds.forEach(roleId -> {
                addUserRoleList.add(new SysUserRole().setUserId(user.getId()).setRoleId(roleId));
            });
            iSysUserRoleService.saveBatch(addUserRoleList);
        }

        // 需要删除的用户的角色ID集合
        List<Long> removeRoleIds = oldRoleIds.stream().filter(roleId -> !newRoleIds.contains(roleId)).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(removeRoleIds)) {
            removeRoleIds.forEach(roleId -> {
                iSysUserRoleService.remove(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, user.getId()).eq(SysUserRole::getRoleId, roleId));
            });
        }

        // 最后更新用户
        return this.updateById(user);
    }

    /**
     * 根据用户名获取认证信息
     *
     * @param username
     * @return
     */
    @Override
    public AuthUserDTO getAuthInfoByUsername(String username) {
        AuthUserDTO userAuthInfo = this.baseMapper.getAuthInfoByUsername(username);
        return userAuthInfo;
    }

    /**
     * 获取用户表单详情
     *
     * @param userId
     * @return
     */
    @Override
    public UserFormVO getUserFormDetail(Long userId) {
        UserFormVO userDetail = this.baseMapper.getUserFormDetail(userId);
        return userDetail;
    }

}
