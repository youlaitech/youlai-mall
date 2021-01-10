package com.youlai.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.admin.pojo.SysPermission;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    @Select(" select id,name,permission from sys_permission ")
    @Results({
            @Result(property = "roleIds",column="id",many = @Many(select="com.youlai.admin.mapper.SysRolePermissionMapper.listByPermissionId"))
    })
    List<SysPermission> listForPermissionRoles();
}
