package com.youlai.mall.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.mall.pms.pojo.domain.PmsSpecification;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PmsSpecificationMapper extends BaseMapper<PmsSpecification> {

    @Select("<script>" +
            " SELECT " +
            "    t1.id,t1.category_id,t1.name,t2.id AS spuId " +
            " FROM " +
            "    pms_specification t1 " +
            "    LEFT JOIN pms_spu t2 ON t1.category_id = t2.category_id " +
            " WHERE " +
            "   t2.id =#{spuId} " +
            "</script>")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(property = "values", column = "{specificationId= t1.id,spuId=spuId}", many = @Many(select = "com.youlai.mall.pms.mapper.PmsSpecificationValueMapper.listByCondition"))
    })
    List<PmsSpecification> listBySpuId(Long spuId);
}
