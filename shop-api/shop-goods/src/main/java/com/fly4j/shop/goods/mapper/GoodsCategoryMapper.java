package com.fly4j.shop.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.shop.goods.pojo.entity.GoodsCategory;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GoodsCategoryMapper extends BaseMapper<GoodsCategory> {

    @Select("<script>" +
            "   select * from  goods_category " +
            "   where 1=1 " +
            "   <if test ='goodsCategory.categoryName!=null and goodsCategory.categoryName.trim() neq \"\"' >" +
            "       and category_name like concat('%'，#{goodsCategory.categoryName}，'%') " +
            "   </if> " +
            "   order by sort asc " +
            "</script>" )
    List<GoodsCategory> page(Page<GoodsCategory> page, GoodsCategory goodsCategory);
}
