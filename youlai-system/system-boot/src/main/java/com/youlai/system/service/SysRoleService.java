package com.youlai.system.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.system.model.entity.SysRole;
import com.youlai.system.model.form.RoleForm;
import com.youlai.system.model.query.RolePageQuery;
import com.youlai.common.web.model.Option;
import com.youlai.system.model.vo.RolePageVO;

import java.util.List;
import java.util.Set;

/**
 * 角色业务接口层
 *
 * @author haoxr
 * @since 2022/6/3
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 角色分页列表
     *
     * @param queryParams 角色查询参数
     * @return {@link Page<RolePageVO>} – 角色分页列表
     */
    Page<RolePageVO> getRolePage(RolePageQuery queryParams);


    /**
     * 角色下拉列表
     *
     * @return {@link List<Option>} – 角色下拉列表
     */
    List<Option> listRoleOptions();

    /**
     * 保存角色
     *
     * @param roleForm 角色表单数据
     * @return {@link Boolean}
     */
    boolean saveRole(RoleForm roleForm);

    /**
     * 获取角色表单数据
     *
     * @param roleId 角色ID
     * @return  {@link RoleForm} – 角色表单数据
     */
    RoleForm getRoleForm(Long roleId);

    /**
     * 修改角色状态
     *
     * @param roleId 角色ID
     * @param status 角色状态(1:启用；0:禁用)
     * @return {@link Boolean}
     */
    boolean updateRoleStatus(Long roleId, Integer status);

    /**
     * 批量删除角色
     *
     * @param ids 角色ID，多个使用英文逗号(,)分割
     * @return {@link Boolean}
     */
    boolean deleteRoles(String ids);

    /**
     * 获取角色的菜单ID集合
     *
     * @param roleId 角色ID
     * @return 菜单ID集合(包括按钮权限ID)
     */
    List<Long> getRoleMenuIds(Long roleId);

    /**
     * 分配角色资源权限
     *
     * @param roleId 角色ID
     * @param menuIds 菜单ID集合
     * @return {@link Boolean}
     */
    boolean assignMenusToRole(Long roleId, List<Long> menuIds);

    /**
     * 获取最大范围的数据权限
     *
     * @param roles 角色编码集合
     * @return {@link Integer} – 最大范围的数据权限
     */
    Integer getMaxDataRangeDataScope(Set<String> roles);


}
