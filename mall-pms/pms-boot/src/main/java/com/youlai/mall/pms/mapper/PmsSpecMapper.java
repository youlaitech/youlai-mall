package com.youlai.mall.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.mall.pms.pojo.domain.PmsSpec;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PmsSpecMapper extends BaseMapper<PmsSpec> {

    @Select("<script>" +
            " SELECT " +
            "    t1.id,t1.category_id,t1.name,t2.id AS spuId " +
            " FROM " +
            "    pms_spec t1 " +
            "    LEFT JOIN pms_spu t2 ON t1.category_id = t2.category_id " +
            " WHERE " +
            "   t2.id =#{spuId} " +
            "</script>")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(property = "values", column = "{specId= t1.id,spuId=spuId}", many = @Many(select = "com.youlai.mall.pms.mapper.PmsSpuSpecValueMapper.listByCondition"))
    })
    List<PmsSpec> listBySpuId(Long spuId);
}
