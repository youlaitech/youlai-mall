package com.youlai.mall.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.mall.pms.pojo.PmsSpecificationValue;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author haoxr
 * @date 2020-11-06
 */
@Mapper
public interface PmsSpecificationValueMapper extends BaseMapper<PmsSpecificationValue> {


    @Select("<script>" +
            " SELECT " +
            "   t1.id, " +
            "   t1.specification_id, " +
            "   t1.value" +
            " FROM " +
            "   pms_specification_value t1 " +
            "   LEFT JOIN pms_sku t2 ON t1.sku_id = t2.id  " +
            " WHERE " +
            "   t2.spu_id = #{spuId}  " +
            "   AND specification_id = #{specificationId} " +
            "</script>")
    List<Integer> listByParentId(Map<String, String> param);

}
