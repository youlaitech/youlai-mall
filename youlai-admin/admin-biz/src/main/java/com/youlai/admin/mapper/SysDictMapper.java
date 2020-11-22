package com.youlai.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.pojo.SysDict;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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

    @Update("<script>" +
            "update sys_dict set type_code = #{newCode},gmt_modified = now() where type_code = #{oldCode}" +
            "</script>")
    int updateTypeCode(@Param("oldCode") String oldCode, @Param("newCode") String newCode);

    /**
     * 根据字典类型id查询管理字典数量
     * @param ids 字典类型id集合
     * @return 关联字典数量
     */
    @Select("<script>" +
            " select count(a.id) from sys_dict a left join sys_dict_type b on a.type_code=b.code " +
            " where b.id in " +
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    Integer countByDictTypeIds(List<Long> ids);
}
