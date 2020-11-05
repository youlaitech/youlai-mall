package com.youlai.mall.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.pms.entity.PmsSpu;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface PmsSpuMapper extends BaseMapper<PmsSpu> {

    @Select("<script>" +
            " SELECT  " +
            " t1.*," +
            " t2.NAME AS category_name" +
            " FROM " +
            "  pms_spu t1" +
            " LEFT JOIN pms_category t2 ON t1.category_id = t2.id" +
            "</script>")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(property = "skuList",column = "id",many = @Many(select="com.youlai.mall.pms.mapper.PmsSkuMapper.listBySpuId"))
    })
    List<PmsSpu> list(Page<PmsSpu> page, PmsSpu spu);
}
