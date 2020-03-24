package com.fly.shop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fly.shop.pojo.dto.GoodsDTO;
import com.fly.shop.pojo.entity.GoodsLadder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: TODO自定义会员阶梯价格Dao
 * @author: Mr.
 * @create: 2020/3/14 15:17
 **/
public interface GoodsLadderMapper extends BaseMapper<GoodsDTO> {

    @Insert("<script>" +
            " INSERT INTO goods_ladder (goods_id, count, discount, price) VALUES " +
            "        <foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">" +
            "            (#{item.goodsId,jdbcType=BIGINT}," +
            "            #{item.count,jdbcType=INTEGER}," +
            "            #{item.discount,jdbcType=DECIMAL}," +
            "            #{item.price,jdbcType=DECIMAL})" +
            "        </foreach>" +
            "</script>")
    int insertList(@Param("list") List<GoodsLadder> goodsLadderList);
}
