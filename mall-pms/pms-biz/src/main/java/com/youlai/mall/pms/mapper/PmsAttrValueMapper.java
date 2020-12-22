package com.youlai.mall.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.mall.pms.pojo.PmsAttrValue;
import com.youlai.mall.pms.pojo.PmsSpecCategory;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author haoxr
 * @date 2020-11-06
 */
@Mapper
public interface PmsAttrValueMapper extends BaseMapper<PmsAttrValue> {


    @Select("<script>" +
            " SELECT " +
            "    t1.id,t2.NAME,t1.VALUE " +
            " FROM " +
            "    pms_attr_value t1 " +
            "    LEFT JOIN pms_attr_category t2 ON t1.attr_category_id = t2.id  " +
            " WHERE " +
            "   t1.spu_id =#{spuId} " +
            "</script>")
    List<PmsAttrValue> listBySpuId(Long spuId);
}
