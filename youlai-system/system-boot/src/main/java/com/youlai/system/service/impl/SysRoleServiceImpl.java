package com.youlai.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.security.util.SecurityUtils;
import com.youlai.system.converter.RoleConverter;
import com.youlai.system.mapper.SysRoleMapper;
import com.youlai.system.pojo.entity.SysRole;
import com.youlai.system.pojo.entity.SysRoleMenu;
import com.youlai.system.pojo.entity.SysRolePermission;
import com.youlai.system.pojo.entity.SysUserRole;
import com.youlai.system.pojo.form.RoleForm;
import com.youlai.system.pojo.form.RoleResourceForm;
import com.youlai.system.pojo.query.RolePageQuery;
import com.youlai.system.pojo.vo.role.RolePageVO;
import com.youlai.system.service.*;
import com.youlai.common.constant.GlobalConstants;
import com.youlai.common.web.domain.Option;
import com.youlai.common.web.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 角色业务实现类
 *
 * @author haoxr
 * @date 2022/6/3
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRoleMenuService sysRoleMenuService;
    private final SysUserRoleService sysUserRoleService;
    private final SysRolePermissionService sysRolePermissionService;
    private final SysPermissionService sysPermissionService;
    private final RoleConverter roleConverter;

    /**
     * 角色分页列表
     *
     * @param queryParams
     * @return
     */
    @Override
    public Page<RolePageVO> listRolePages(RolePageQuery queryParams) {
        // 查询参数
        Page<SysRole> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        // 查询数据
        page = this.baseMapper.listRolePages(page, queryParams);

        // 实体转换
        Page<RolePageVO> pageResult = roleConverter.entity2Page(page);
        return pageResult;
    }

    /**
     * 角色下拉列表
     *
     * @return
     */
    @Override
    public List<Option> listRoleOptions() {
        // 查询数据
        List<SysRole> roleList = this.list(new LambdaQueryWrapper<SysRole>()
                // 非超级管理员用户超级管理员角色不可见
                .ne(SecurityUtils.isRoot() == false, SysRole::getCode, GlobalConstants.ROOT_ROLE_CODE)
                .eq(SysRole::getStatus, GlobalConstants.STATUS_YES)
                .select(SysRole::getId, SysRole::getName)
                .orderByAsc(SysRole::getSort)
        );

        // 实体转换
        List<Option> list = roleConverter.roles2Options(roleList);
        return list;
    }

    /**
     * @param roleForm
     * @return
     */
    @Override
    public boolean saveRole(RoleForm roleForm) {

        Long roleId = roleForm.getId();
        String roleCode = roleForm.getCode();

        int count = this.count(new LambdaQueryWrapper<SysRole>()
                .ne(roleId != null, SysRole::getId, roleId)
                .and(wrapper ->
                        wrapper.eq(SysRole::getCode, roleCode).or().eq(SysRole::getName, roleCode)
                ));
        Assert.isTrue(count == 0, "角色名称或角色编码重复，请检查！");

        // 实体转换
        SysRole role = roleConverter.form2Entity(roleForm);

        boolean result = this.saveOrUpdate(role);
        // 刷新权限缓存
        if (result) {
            sysPermissionService.refreshPermRolesRules();
        }
        return result;
    }

    /**
     * 修改角色状态
     *
     * @param roleId
     * @param status
     * @return
     */
    @Override
    public boolean updateRoleStatus(Long roleId, Integer status) {
        boolean result = this.update(new LambdaUpdateWrapper<SysRole>()
                .eq(SysRole::getId, roleId)
                .set(SysRole::getStatus, status));
        // 修改角色成功刷新权限缓存
        if (result) {
            sysPermissionService.refreshPermRolesRules();
        }
        return result;
    }

    /**
     * 批量删除角色
     *
     * @param ids
     * @return
     */
    @Override
    public boolean deleteRoles(String ids) {
        List<Long> roleIds = Arrays.asList(ids.split(",")).stream().map(id -> Long.parseLong(id)).collect(Collectors.toList());
        Optional.ofNullable(roleIds)
                .orElse(new ArrayList<>())
                .forEach(id -> {
                    int count = sysUserRoleService.count(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getRoleId, id));
                    Assert.isTrue(count <= 0, "该角色已分配用户，无法删除");
                    sysRoleMenuService.remove(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, id));
                    sysRolePermissionService.remove(new LambdaQueryWrapper<SysRolePermission>().eq(SysRolePermission::getRoleId, id));
                });


        boolean result = this.removeByIds(roleIds);
        // 删除成功，刷新权限缓存
        if (result) {
            sysPermissionService.refreshPermRolesRules();
        }
        return result;
    }

    /**
     * 获取角色拥有的菜单ID集合
     *
     * @param roleId
     * @return
     */
    @Override
    public List<Long> getRoleResourceIds(Long roleId) {
        List<Long> menuIds = sysRoleMenuService.listMenuIdsByRoleId(roleId);
        return menuIds;
    }

    /**
     * 修改角色的资源权限
     *
     * @param roleId
     * @param resourceIds
     * @return
     */
    @Override
    @Transactional
    @CacheEvict(cacheNames = "system", key = "'routes'")
    public boolean updateRoleResourceIds(Long roleId, List<Long> resourceIds) {
        // 删除角色菜单
        sysRoleMenuService.remove(new LambdaQueryWrapper<SysRoleMenu>()
                .eq(SysRoleMenu::getRoleId, roleId));
        // 新增角色菜单
        if (CollectionUtil.isNotEmpty(resourceIds)) {
            List<SysRoleMenu> roleMenus = resourceIds.stream()
                    .map(menuId -> new SysRoleMenu(roleId, menuId))
                    .collect(Collectors.toList());
            sysRoleMenuService.saveBatch(roleMenus);
        }
        return true;
    }

}