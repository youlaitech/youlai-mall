package com.fly.shop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly.shop.pojo.entity.GoodsAttribute;
import com.fly.shop.pojo.entity.GoodsCategory;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GoodsAttributeMapper extends BaseMapper<GoodsAttribute> {


    @Select("<script>" +
            "   SELECT  a.*, b.attribute_type_name" +
            "   FROM goods_attribute a "+
            "   LEFT JOIN goods_attribute_type b ON a.attribute_type_id = b.attribute_type_id "+
            "   WHERE 1=1 "+
            "   <if test='goodsAttribute.attributeTypeId!=null'> "+
            "       AND a.attribute_type_id=#{goodsAttribute.attributeTypeId} "+
            "   </if>"+
            "   <if test='goodsAttribute.type!=null'> "+
            "       AND a.type=#{goodsAttribute.type} "+
            "   </if>"+
            "   ORDER BY a.sort asc,a.update_time desc,a.create_time desc "+
            "</script>" )
    List<GoodsAttribute> page(Page<GoodsAttribute> page, GoodsAttribute goodsAttribute);


}
