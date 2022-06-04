package com.youlai.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.admin.pojo.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色<->菜单持久层
 *
 * @author haoxr
 * @date 2022/6/4
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    @Select("<script>" +
            "  select role_id from sys_role_menu where menu_id=#{menuId} " +
            "</script>")
    List<Integer> listByMenuId(Integer menuId);


    /**
     * 获取角色拥有的菜单ID集合
     *
     * @param roleId
     * @return
     */
    List<Long> listMenuIdsByRoleId(Long roleId);
}
