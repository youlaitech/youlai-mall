package com.youlai.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.admin.pojo.entity.SysRolePermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {


    /**
     * 获取角色拥有的权限ID集合
     *
     * @param roleId
     * @return
     */
    List<Long> listPermIdsByRoleId(Long roleId);
}
