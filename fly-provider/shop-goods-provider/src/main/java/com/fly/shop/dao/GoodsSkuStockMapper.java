package com.fly.shop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fly.shop.pojo.entity.GoodsSkuStock;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description: TODO自定义会员阶梯价格Dao
 * @author: Mr.
 * @create: 2020/3/14 15:17
 **/
public interface GoodsSkuStockMapper extends BaseMapper<GoodsSkuStock> {

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
    int insertList(@Param("list") List<GoodsSkuStock> goodsLadderList);

    @Select("<script>" +
            "SELECT * FROM goods_sku_stock WHERE goods_id = #{goodsId}" +
            "<if test='keyword!=null'> " +
            " AND skuCode like '%${keyword}%'" +
            "</if>"+
            "</script>")
    List<GoodsSkuStock> getList(@Param("goodsId")Long goodsId, @Param("keyword")String keyword);

    @Insert("<script>" +
            " REPLACE INTO goods_sku_stock (id,goods_id, sku_code, price, stock, low_stock,pic, sale, sp_data) VALUES" +
            "   <foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">" +
            "       (#{item.id,jdbcType=BIGINT}," +
            "       #{item.goodsId,jdbcType=BIGINT}," +
            "       #{item.skuCode,jdbcType=VARCHAR}," +
            "       #{item.price,jdbcType=DECIMAL}," +
            "       #{item.stock,jdbcType=INTEGER}," +
            "       #{item.lowStock,jdbcType=INTEGER}," +
            "       #{item.pic,jdbcType=VARCHAR}," +
            "       #{item.sale,jdbcType=INTEGER}," +
            "       #{item.spData,jdbcType=VARCHAR})" +
            "   </foreach>"+
            "</script>")
    boolean replaceList(@Param("goodsId")Long goodsId, @Param("list")List<GoodsSkuStock> skuStockList);
}
