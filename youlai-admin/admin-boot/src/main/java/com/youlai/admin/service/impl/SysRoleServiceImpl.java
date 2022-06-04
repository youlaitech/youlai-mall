package com.youlai.admin.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.admin.convert.SysRoleConvert;
import com.youlai.admin.mapper.SysRoleMapper;
import com.youlai.admin.pojo.entity.SysRole;
import com.youlai.admin.pojo.entity.SysRoleMenu;
import com.youlai.admin.pojo.entity.SysRolePermission;
import com.youlai.admin.pojo.entity.SysUserRole;
import com.youlai.admin.pojo.form.RoleForm;
import com.youlai.admin.pojo.query.RolePageQuery;
import com.youlai.admin.pojo.vo.role.RolePageVO;
import com.youlai.admin.service.*;
import com.youlai.common.constant.GlobalConstants;
import com.youlai.common.web.domain.Option;
import com.youlai.common.web.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private final SysRoleConvert sysRoleConvert;

    /**
     * 角色分页列表
     *
     * @param queryParams
     * @return
     */
    @Override
    public Page<RolePageVO> listPageRoles(RolePageQuery queryParams) {
        // 查询数据
        Page<SysRole> rolePage = this.page(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                new LambdaQueryWrapper<SysRole>()
                        .eq(StrUtil.isNotBlank(queryParams.getName()), SysRole::getName, queryParams.getName())
                        .ne(!UserUtils.isRoot(), SysRole::getCode,GlobalConstants.ROOT_ROLE_CODE) // 非超级管理员不显示超级管理员角色
                        .select(SysRole::getId, SysRole::getName, SysRole::getCode)
        );
        // 实体转换
        return sysRoleConvert.role2Page(rolePage);
    }


    /**
     * 角色下拉列表
     *
     * @return
     */
    @Override
    public List<Option> listSelectRoles() {
        // 查询数据
        List<SysRole> roleList = this.list(new LambdaQueryWrapper<SysRole>()
                .ne(!UserUtils.isRoot(), SysRole::getCode, GlobalConstants.ROOT_ROLE_CODE)
                .select(SysRole::getId, SysRole::getName)
                .orderByAsc(SysRole::getSort)
        );

        // 实体转换
        List<Option> list = sysRoleConvert.roles2Options(roleList);
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
        SysRole role = sysRoleConvert.form2Entity(roleForm);

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
     * 获取角色的资源ID集合,资源包括菜单(m_id)和权限(p_id)
     *
     * @param roleId
     * @return
     */
    @Override
    public List<String> listRoleResourceIds(Long roleId) {

        String menuPrefix = "m_", permPrefix = "p_"; // 菜单和权限都属于资源，合并使用不同前缀进行区分

        // 获取角色拥有的菜单ID集合
        List<Long> menuIds = sysRoleMenuService.listMenuIdsByRoleId(roleId);
        List<String> convertedMenuIds = Optional.ofNullable(menuIds).orElse(new ArrayList<>()).stream().map(menuId -> menuPrefix + menuId).collect(Collectors.toList());

        // 获取角色拥有的权限ID集合
        List<Long> permIds = sysRolePermissionService.listPermIdsByRoleId(roleId);
        List<String> convertedPermIds = Optional.ofNullable(permIds).orElse(new ArrayList<>()).stream().map(permId -> permPrefix + permId).collect(Collectors.toList());

        // 资源ID(菜单+权限)合并
        convertedMenuIds.addAll(convertedPermIds);
        return convertedMenuIds;
    }

}
