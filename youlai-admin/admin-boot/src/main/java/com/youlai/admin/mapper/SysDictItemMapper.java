package com.youlai.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.pojo.entity.SysDictItem;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysDictItemMapper extends BaseMapper<SysDictItem> {

    @Select("<script>" +
            " select a.*,b.name as dict_name from sys_dict_item a " +
            " left join sys_dict b on a.dict_code=b.code " +
            " where 1=1 " +
            " <if test='dictItem.name != null and dictItem.name.trim() neq \"\"'>" +
            "   and a.name like concat('%',#{dictItem.name},'%')" +
            " </if>" +
            " <if test='dictItem.dictCode !=null and dictItem.dictCode.trim() neq \"\"'>" +
            "   and a.dict_code = #{dictItem.dictCode}" +
            " </if>" +
            "</script>")
    List<SysDictItem> list(Page<SysDictItem> page, SysDictItem dictItem);
}
