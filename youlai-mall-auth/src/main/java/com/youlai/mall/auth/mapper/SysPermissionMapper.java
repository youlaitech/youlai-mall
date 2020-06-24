package com.youlai.mall.auth.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.mall.auth.entity.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    @Select("SELECT e.permission_id,e.permission_name, e.perms  " +
            "    FROM  sys_user a " +
            "    INNER JOIN sys_user_role b ON b.user_id = a.user_id " +
            "    INNER JOIN sys_role c ON c.role_id = b.role_id " +
            "    INNER JOIN sys_role_permission d ON d.role_id = c.role_id " +
            "    INNER JOIN sys_permission e ON e.permission_id = d.permission_id " +
            "    WHERE a.user_id =#{userId}")
    List<SysPermission> selectByUserId(Long userId);
}
