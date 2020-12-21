package com.youlai.mall.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.mall.pms.pojo.PmsSku;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface PmsSkuMapper extends BaseMapper<PmsSku> {

    @Select("<script>" +
            "  SELECT\n" +
            "\tt1.*,\n" +
            "\tgroup_concat( t2.id ) AS specificationValueIds \n" +
            "FROM\n" +
            "\tpms_sku t1\n" +
            "\tLEFT JOIN pms_specification_value t2 ON t1.id = t2.sku_id \n" +
            "GROUP BY\n" +
            "\tt1.id" +
            "</script>")
    List<PmsSku> listBySpuId(Integer menuId);

}
