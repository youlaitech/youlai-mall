package com.youlai.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.domain.entity.SysDict;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface SysDictMapper extends BaseMapper<SysDict> {

    @Select("<script>" +
            " select a.*,b.name as type_name from sys_dict a left join sys_dict_type b on a.type_code=b.code " +
            " where 1=1 " +
            " <if test='dict.name != null and dict.name.trim() neq \"\"'>" +
            "   and a.name like concat('%',#{dict.name},'%')" +
            " </if>" +
            " <if test='dict.typeCode !=null and dict.typeCode.trim() neq \"\"'>" +
            "   and a.type_code = #{dict.typeCode}" +
            " </if>" +
            "</script>")
    List<SysDict> list(Page<SysDict> page, SysDict dict);
}
