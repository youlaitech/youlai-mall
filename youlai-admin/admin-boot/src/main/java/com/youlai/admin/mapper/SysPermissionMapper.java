package com.youlai.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.pojo.entity.SysPermission;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {


    List<SysPermission> listPermRoles();

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



    List<String> listPermByRoles(List<String> roles);
}
