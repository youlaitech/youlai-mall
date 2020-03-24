package com.fly.shop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fly.shop.pojo.dto.GoodsDTO;
import com.fly.shop.pojo.entity.GoodsFullReduction;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @Description: TODO自定义商品满减Dao
* @author: Mr.
* @create: 2020/3/14 15:17
**/
public interface GoodsFullReductionMapper extends BaseMapper<GoodsDTO> {

    @Insert("<script>" +
            "INSERT INTO goods_full_reduction (goods_id, full_price, reduce_price) VALUES" +
            "        <foreach collection=\"list\" separator=\",\" item=\"item\" index=\"index\">" +
            "            (#{item.goodsId,jdbcType=BIGINT}," +
            "            #{item.fullPrice,jdbcType=DECIMAL}," +
            "            #{item.reducePrice,jdbcType=DECIMAL})" +
            "        </foreach>"+
            "</script>")
    int insertList(@Param("list") List<GoodsFullReduction> goodsFullReductionList);
}
