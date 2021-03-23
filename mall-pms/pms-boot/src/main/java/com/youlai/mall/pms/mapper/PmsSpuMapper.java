package com.youlai.mall.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.pms.pojo.domain.PmsSpu;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author haoxr
 * @date 2020-11-06
 */
@Mapper
public interface PmsSpuMapper extends BaseMapper<PmsSpu> {

    @Select("<script>" +
            " SELECT  " +
            " t1.*," +
            " t2.NAME AS category_name ," +
            " t3.NAME AS brand_name " +
            " FROM " +
            "  pms_spu t1" +
            " LEFT JOIN pms_category t2 ON t1.category_id = t2.id " +
            " LEFT JOIN pms_brand t3 ON t1.brand_id = t3.id " +
            " WHERE 1=1 " +
            " <if test ='spu.categoryId !=null and spu.categoryId > 0' >" +
            "       AND t2.id = #{spu.categoryId} " +
            " </if>" +
            " ORDER BY t1.gmt_create DESC" +
            "</script>")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(property = "skus",column = "id",many = @Many(select="com.youlai.mall.pms.mapper.PmsSkuMapper.listBySpuId"))
    })
    List<PmsSpu> list(Page<PmsSpu> page, PmsSpu spu);
}
