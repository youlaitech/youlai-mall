package com.youlai.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.admin.pojo.entity.SysPermission;
import com.youlai.admin.pojo.query.PermissionPageQuery;
import com.youlai.admin.pojo.vo.permission.PermissionPageVO;

import java.util.List;

/**
 * 权限业务接口
 *
 * @author haoxr
 * @date 2022/1/22
 */
public interface ISysPermissionService extends IService<SysPermission> {

    List<SysPermission> listPermRoles();

    /**
     * 根据角色编码集合获取按钮权限标识
     *
     * @param roles 角色权限编码集合
     * @return
     */
    List<String> listBtnPermByRoles(List<String> roles);

    /**
     * 刷新Redis缓存中角色菜单的权限规则，角色和菜单信息变更调用
     */
    boolean refreshPermRolesRules();

    /**
     * 获取权限分页列表
     *
     * @param permissionPageQuery
     * @return
     */
    IPage<PermissionPageVO> listPermissionsWithPage(PermissionPageQuery permissionPageQuery);
}
