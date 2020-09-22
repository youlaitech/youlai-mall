package com.youlai.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.admin.api.entity.SysRoleResource;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface SysRoleResourceMapper extends BaseMapper<SysRoleResource> {


    @Select("<script>" +
            "   select role_id from sys_role_resource where resource_id=#{resourceId} " +
            "</script>")
    List<Integer> listByResourceId(Integer resourceId);

}
