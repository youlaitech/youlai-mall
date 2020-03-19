package com.fly.shop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fly.shop.pojo.dto.GoodsDTO;
import com.fly.shop.pojo.vo.GoodsAttributeValueVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: TODO自定义会员阶梯价格Dao
 * @author: Mr.
 * @create: 2020/3/14 15:17
 **/
public interface GoodsAttributeValueMapper extends BaseMapper<GoodsDTO> {

    @Insert("<script>" +
            "  insert into goods_attribute_value (goods_id,goods_attribute_id,value) values" +
            "        <foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">" +
            "            (#{item.goodsId,jdbcType=BIGINT}," +
            "            #{item.goodsAttributeId,jdbcType=BIGINT}," +
            "            #{item.value,jdbcType=VARCHAR})" +
            "        </foreach>"+
            "</script>")
    int insertList(@Param("list") List<GoodsAttributeValueVO> goodsLadderList);
}
