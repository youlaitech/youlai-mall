package com.youlai.mall.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.mall.pms.pojo.domain.PmsProductSpecValue;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author haoxr
 * @date 2020-11-06
 */
@Mapper
public interface PmsProductSpecValueMapper extends BaseMapper<PmsProductSpecValue> {


    @Select("<script>" +
            " SELECT " +
            "   id, " +
            "   spu_id, " +
            "   spec_id, " +
            "   value" +
            " FROM " +
            "   pms_spu_spec_value " +
            " WHERE " +
            "   spu_id = #{spuId}  " +
            "   AND spec_id = #{specId} " +
            "</script>")
    List<PmsProductSpecValue> listBySpuIdAndSpecId(Map<String, String> param);

}
