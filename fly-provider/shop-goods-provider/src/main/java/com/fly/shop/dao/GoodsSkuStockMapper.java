package com.fly.shop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fly.shop.pojo.dto.GoodsDTO;
import com.fly.shop.pojo.vo.GoodsSkuStockVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: TODO自定义会员阶梯价格Dao
 * @author: Mr.
 * @create: 2020/3/14 15:17
 **/
public interface GoodsSkuStockMapper extends BaseMapper<GoodsDTO> {

    @Insert("<script>" +
            "INSERT INTO goods_sku_stock (goods_id, sku_code, price, stock, low_stock, pic, sale, sp_data) VALUES" +
            "        <foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">" +
            "            (#{item.goodsId,jdbcType=BIGINT}," +
            "            #{item.skuCode,jdbcType=VARCHAR}," +
            "            #{item.price,jdbcType=DECIMAL}," +
            "            #{item.stock,jdbcType=INTEGER}," +
            "            #{item.lowStock,jdbcType=INTEGER}," +
            "            #{item.pic,jdbcType=VARCHAR}," +
            "            #{item.sale,jdbcType=INTEGER}," +
            "            #{item.spData,jdbcType=VARCHAR})" +
            "        </foreach>"+
            "</script>")
    int insertList(@Param("list") List<GoodsSkuStockVO> goodsLadderList);
}
