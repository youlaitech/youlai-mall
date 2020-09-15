package com.youlai.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.admin.domain.entity.SysMenu;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    @Select("<script>" +
            "   select id ,name ,parent_id,path,component,perms,icon,sort,visible,status from sys_menu " +
            "   where status=1 and type in (0,1)" +
            "   order by sort asc" +
            "</script>")
    @Results({
            @Result(id=true, column="id", property="id"),
            @Result(property = "roles",column="id",many = @Many(select="com.youlai.admin.mapper.SysRoleMenuMapper.listByMenuId"))
    })
    List<SysMenu> listForRouter();
}
