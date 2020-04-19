package com.fly4j.yshop.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.yshop.pms.pojo.dto.PmsSkuDTO;
import com.fly4j.yshop.pms.pojo.entity.PmsSku;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

public interface PmsSkuMapper extends BaseMapper<PmsSku> {


    @Select("<script>"
                 +" SELECT"
                 +"     a.id,"
                 +"     a.specs,"
                 +"     a.price,"
                 +"     a.pic_url,"
                 +"     ( a.stock - a.stock_locked ) AS stock,"
                 +"     b.name AS spu_name,"
                 +"     b.code AS spu_code"
                 +" FROM"
                 +" pms_sku a"
                 +" LEFT JOIN pms_spu b ON a.spu_id = b.id"
                 +" ORDER BY a.create_time"
                 +"</script>")
    Page<PmsSkuDTO> page(Map<String, Object> params, Page<PmsSku> page);
}
