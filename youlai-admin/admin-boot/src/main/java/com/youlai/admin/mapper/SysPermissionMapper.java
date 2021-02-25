package com.youlai.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.pojo.entity.SysPermission;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    @Select(" select id, name,perm,method from sys_permission where type=1 ")
    @Results({
            @Result(property = "roleIds", column = "id", many = @Many(select = "com.youlai.admin.mapper.SysRolePermissionMapper.listRoleIds"))
    })
    List<SysPermission> listPermissionRoles();

    @Select({
            "<script>",
            " SELECT ",
            " 	t1.*, t2.NAME AS module_name  ",
            " FROM ",
            " 	sys_permission t1 ",
            " 	LEFT JOIN sys_menu t2 ON t1.module_id = t2.id ",
            " WHERE 1=1 ",
            " <if test='permission.name != null and permission.name.trim() neq \"\"'>",
            "   and t1.name like concat('%',#{permission.name},'%')",
            " </if>",
            " <if test='permission.type !=null '>",
            "   and t1.type = #{permission.type}",
            " </if>",
            " <if test='permission.moduleId !=null '>",
            "   and t1.module_id = #{permission.moduleId}",
            " </if>",
            " ORDER BY t1.gmt_modified DESC,t1.gmt_create DESC",
            "</script>"
    })
    List<SysPermission> list(Page<SysPermission> page, SysPermission permission);


    @Select({
            "<script>",
            " SELECT ",
            " 	perm  ",
            " FROM ",
            " 	sys_permission t1 ",
            " 	LEFT JOIN sys_role_permission t2 ON t1.id = t2.permission_id  ",
            " WHERE ",
            " 	t1.type = #{type}  ",
            " 	AND t2.role_id IN ",
            "       <foreach collection='roleIds' item='roleId' open='(' separator=',' close=')'>",
            "           #{roleId}",
            "       </foreach>",
            "</script>"
    })
    List<String> listPermsByRoleIds(List<Long> roleIds, Integer type);
}
