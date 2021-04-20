package com.youlai.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.pojo.entity.SysUser;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {


    @Select("<script> " +
            "   select u.*,d.name as dept_name , GROUP_CONCAT(r.name) as roleNames" +
            "   from sys_user u " +
            "       left join sys_dept d on u.dept_id= d.id " +
            "       left join sys_user_role ur on u.id=ur.user_id " +
            "       left join sys_role r on ur.role_id=r.id " +
            "   where deleted != 1 " +
            " <if test ='user.username!=null and user.username.trim() neq \"\"'>" +
            "       and u.username like concat('%',#{user.username},'%')" +
            " </if>" +
            " <if test ='user.mobile!=null and user.mobile.trim() neq \"\"'>" +
            "       and u.mobile like concat('%',#{user.mobile},'%')" +
            " </if>" +
            " <if test ='user.status!=null and user.status>0'>" +
            "       and u.status = #{user.status}" +
            " </if>" +
            " <if test ='user.deptId!=null and user.deptId > 0'>" +
            "       and  concat(',',concat(d.tree_path,',',d.id),',')  like concat('%,',#{user.deptId},',%')" +
            " </if>" +
            " GROUP BY u.id " +
            "</script>")

    @Results({
            @Result(id = true, column = "id", property = "id")
    })
    List<SysUser> list(Page<SysUser> page, SysUser user);

}
