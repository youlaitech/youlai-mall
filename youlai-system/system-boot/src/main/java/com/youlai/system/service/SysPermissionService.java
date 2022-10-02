package com.youlai.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.system.pojo.entity.SysPermission;
import com.youlai.system.pojo.query.PermPageQuery;
import com.youlai.system.pojo.vo.perm.PermPageVO;

import java.util.List;

/**
 * 权限业务接口
 *
 * @author haoxr
 * @date 2022/1/22
 */
public interface SysPermissionService extends IService<SysPermission> {

    /**
     * 权限<->有权限的角色集合
     *
     * @return
     */
    List<SysPermission> listPermRoles();

    /**
     * 刷新Redis缓存中角色菜单的权限规则，角色和菜单信息变更调用
     */
    boolean refreshPermRolesRules();

    /**
     * 获取权限分页列表
     *
     * @param permPageQuery
     * @return
     */
    IPage<PermPageVO> listPermPages(PermPageQuery permPageQuery);

}
