package com.fly.shop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fly.shop.pojo.entity.PmsBrand;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface PmsBrandMapper extends BaseMapper<PmsBrand> {

    @Select("select * from pms_brand")
    List<PmsBrand> findAll();
}
