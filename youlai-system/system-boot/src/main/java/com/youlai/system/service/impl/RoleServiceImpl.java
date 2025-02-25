package com.youlai.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.constant.SystemConstants;
import com.youlai.common.security.util.SecurityUtils;
import com.youlai.system.converter.RoleConverter;
import com.youlai.system.mapper.RoleMapper;
import com.youlai.system.model.entity.Role;
import com.youlai.system.model.entity.RoleMenu;
import com.youlai.system.model.form.RoleForm;
import com.youlai.system.model.query.RolePageQuery;
import com.youlai.common.core.model.Option;
import com.youlai.system.model.vo.RolePageVO;
import com.youlai.system.service.RoleMenuService;
import com.youlai.system.service.RoleService;
import com.youlai.system.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色业务实现类
 *
 * @author Ray.Hao
 * @since 2022/6/3
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final RoleMenuService roleMenuService;
    private final UserRoleService userRoleService;
    private final RoleConverter roleConverter;

    /**
     * 角色分页列表
     *
     * @param queryParams 角色查询参数
     * @return {@link Page<RolePageVO>} – 角色分页列表
     */
    @Override
    public Page<RolePageVO> getRolePage(RolePageQuery queryParams) {
        // 查询参数
        int pageNum = queryParams.getPageNum();
        int pageSize = queryParams.getPageSize();
        String keywords = queryParams.getKeywords();

        // 查询数据
        Page<Role> rolePage = this.page(new Page<>(pageNum, pageSize),
                new LambdaQueryWrapper<Role>()
                        .and(StrUtil.isNotBlank(keywords),
                                wrapper ->
                                        wrapper.like(StrUtil.isNotBlank(keywords), Role::getName, keywords)
                                                .or()
                                                .like(StrUtil.isNotBlank(keywords), Role::getCode, keywords)
                        )
                        .ne(!SecurityUtils.isRoot(), Role::getCode, SystemConstants.ROOT_ROLE_CODE) // 非超级管理员不显示超级管理员角色
        );

        // 实体转换
        Page<RolePageVO> pageResult = roleConverter.toPageVo(rolePage);
        return pageResult;
    }

    /**
     * 角色下拉列表
     *
     * @return {@link List<Option>} – 角色下拉列表
     */
    @Override
    public List<Option> listRoleOptions() {
        // 查询数据
        List<Role> roleList = this.list(new LambdaQueryWrapper<Role>()
                .ne(!SecurityUtils.isRoot(), Role::getCode, SystemConstants.ROOT_ROLE_CODE)
                .select(Role::getId, Role::getName)
                .orderByAsc(Role::getSort)
        );

        // 实体转换
        return roleConverter.toOption(roleList);
    }

    /**
     * 保存角色
     *
     * @param roleForm 角色表单数据
     * @return {@link Boolean}
     */
    @Override
    public boolean saveRole(RoleForm roleForm) {

        Long roleId = roleForm.getId();

        // 编辑角色时，判断角色是否存在
        Role oldRole = null;
        if (roleId != null) {
            oldRole = this.getById(roleId);
            Assert.isTrue(oldRole != null, "角色不存在");
        }

        String roleCode = roleForm.getCode();
        long count = this.count(new LambdaQueryWrapper<Role>()
                .ne(roleId != null, Role::getId, roleId)
                .and(wrapper ->
                        wrapper.eq(Role::getCode, roleCode).or().eq(Role::getName, roleForm.getName())
                ));
        Assert.isTrue(count == 0, "角色名称或角色编码已存在，请修改后重试！");

        // 实体转换
        Role role = roleConverter.toEntity(roleForm);

        boolean result = this.saveOrUpdate(role);
        if (result) {
            // 判断角色编码或状态是否修改，修改了则刷新权限缓存
            if (oldRole != null
                    && (
                    !StrUtil.equals(oldRole.getCode(), roleCode) ||
                            !ObjectUtil.equals(oldRole.getStatus(), roleForm.getStatus())
            )) {
                roleMenuService.refreshRolePermsCache(oldRole.getCode(), roleCode);
            }
        }
        return result;
    }

    /**
     * 获取角色表单数据
     *
     * @param roleId 角色ID
     * @return  {@link RoleForm} – 角色表单数据
     */
    @Override
    public RoleForm getRoleForm(Long roleId) {
        Role entity = this.getById(roleId);
        return roleConverter.toForm(entity);
    }

    /**
     * 修改角色状态
     *
     * @param roleId 角色ID
     * @param status 角色状态(1:启用；0:禁用)
     * @return {@link Boolean}
     */
    @Override
    public boolean updateRoleStatus(Long roleId, Integer status) {
        Role role = this.getById(roleId);
        Assert.isTrue(role != null, "角色不存在");

        role.setStatus(status);
        boolean result = this.updateById(role);
        if (result) {
            // 刷新角色的权限缓存
            roleMenuService.refreshRolePermsCache(role.getCode());
        }

        return result;
    }

    /**
     * 批量删除角色
     *
     * @param ids 角色ID，多个使用英文逗号(,)分割
     * @return
     */
    @Override
    public boolean deleteRoles(String ids) {
        List<Long> roleIds = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();

        for (Long roleId : roleIds) {
            Role role = this.getById(roleId);
            Assert.isTrue(role != null, "角色不存在");

            // 判断角色是否被用户关联
            boolean isRoleAssigned = userRoleService.hasAssignedUsers(roleId);
            Assert.isTrue(!isRoleAssigned, "角色【{}】已分配用户，请先解除关联后删除", role.getName());

            boolean deleteResult = this.removeById(roleId);
            if (deleteResult) {
                // 删除成功，刷新权限缓存
                roleMenuService.refreshRolePermsCache(role.getCode());
            }
        }
        return true;
    }

    /**
     * 获取角色的菜单ID集合
     *
     * @param roleId 角色ID
     * @return 菜单ID集合(包括按钮权限ID)
     */
    @Override
    public List<Long> getRoleMenuIds(Long roleId) {
        return roleMenuService.listMenuIdsByRoleId(roleId);
    }

    /**
     * 分配角色资源权限
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID集合
     * @return {@link Boolean}
     */
    @Override
    @Transactional
    @CacheEvict(cacheNames = "menu", key = "'routes'")
    public boolean assignMenusToRole(Long roleId, List<Long> menuIds) {
        Role role = this.getById(roleId);
        Assert.isTrue(role != null, "角色不存在");

        // 删除角色菜单
        roleMenuService.remove(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, roleId));
        // 新增角色菜单
        if (CollectionUtil.isNotEmpty(menuIds)) {
            List<RoleMenu> roleMenus = menuIds.stream()
                    .map(menuId -> new RoleMenu(roleId, menuId))
                    .collect(Collectors.toList());
            roleMenuService.saveBatch(roleMenus);
        }

        // 刷新角色的权限缓存
        roleMenuService.refreshRolePermsCache(role.getCode());

        return true;
    }

    /**
     * 获取最大范围的数据权限
     *
     * @param roles
     * @return
     */
    @Override
    public Integer getMaxDataRangeDataScope(Set<String> roles) {
        Integer dataScope = this.baseMapper.getMaxDataRangeDataScope(roles);
        return dataScope;
    }

}
