package com.youlai.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.system.model.bo.RolePermsBO;
import com.youlai.system.model.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * 角色菜单访问层
 *
 * @author haoxr
 * @since 2022/6/4
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    /**
     * 获取角色拥有的菜单ID集合
     *
     * @param roleId 角色ID
     * @return 菜单ID集合
     */
    List<Long> listMenuIdsByRoleId(Long roleId);

    /**
     * 获取权限和拥有权限的角色列表
     */
    List<RolePermsBO> getRolePermsList(String roleCode);


    /**
     * 获取角色权限集合
     *
     * @param roles
     * @return
     */
    Set<String> listRolePerms(Set<String> roles);
}
