package com.fly4j.shop.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fly4j.shop.goods.pojo.entity.GoodsBrand;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface GoodsBrandMapper extends BaseMapper<GoodsBrand> {

    @Select("select * from goods_brand order by sort desc")
    List<GoodsBrand> findAll();
}
