package com.youlai.mall.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.mall.pms.pojo.domain.PmsSku;
import com.youlai.mall.pms.pojo.dto.SkuDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PmsSkuMapper extends BaseMapper<PmsSku> {

    @Select("<script>" +
            "  select * from pms_sku where spu_id=#{spuId}" +
            "</script>")
    List<PmsSku> listBySpuId(Long spuId);


    @Select({
            "<script>",
            " SELECT",
            "  t1.id,",
            "  t1.CODE,",
            "  t1.NAME,",
            "  t1.pic,",
            "  t1.origin_price,",
            "  t1.price price,",
            "  t1.inventory inventory,",
            "  t2.id spu_id,",
            "  t2.NAME product_name,",
            "  t2.pic product_pic,",
            "  t3.id category_id,",
            "  t3.NAME category_name,",
            "  t4.id brand_id,",
            "  t4.NAME brand_name",
            " FROM",
            "  pms_sku t1",
            "  LEFT JOIN pms_spu t2 ON t1.spu_id = t2.id",
            "  LEFT JOIN pms_category t3 ON t2.category_id = t3.id",
            "  LEFT JOIN pms_brand t4 ON t2.brand_id = t4.id",
            " WHERE t1.id in (#{skuIds})",
            "</script>"
    })
    List<SkuDTO> listBySkuIds(String skuIds);
}
