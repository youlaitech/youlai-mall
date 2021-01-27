package com.youlai.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.admin.pojo.entity.SysRole;
import com.youlai.admin.pojo.entity.SysUser;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {


    @Select("<script>" +
            " SELECT * from sys_role where user_id =#{userId} " +
            "</script>")
    List<SysUser> listByUserId(Long userId);

}
