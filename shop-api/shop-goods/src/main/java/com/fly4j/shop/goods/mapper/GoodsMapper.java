package com.fly4j.shop.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.shop.goods.pojo.dto.SpikeGoodsDTO;
import com.fly4j.shop.goods.pojo.entity.Goods;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface GoodsMapper extends BaseMapper<Goods> {
    @Update("UPDATE goods SET publish_status = #{publishStatus} WHERE id = #{id}")
    void updatePublishStatus(Goods goods);

    @Update("UPDATE goods SET new_status = #{newStatus} WHERE id = #{id}")
    void updateNewStatus(Goods goods);

    @Update("UPDATE goods SET recommend_status = #{recommendStatus} WHERE id = #{id}")
    void updateRecommendStatus(Goods goods);

    @Update("UPDATE goods SET delete_status = #{deleteStatus} WHERE id = #{id}")
    void updateDeleteStatus(Goods goods);




    @Select("<script>" +
            " select id as goodsId,name as goodsName,goods_sn,price as goodsPrice, stock as goodsStock from goods " +
            "  where 1=1 " +
            "   <if test='spikeGoodsDTO.goodsName !=null and spikeGoodsDTO.goodsName.trim() neq \"\"'>" +
            "        and name like concat('%',#{spikeGoodsDTO.goodsName},'%') " +
            "   </if> " +
            " order by sort asc" +
            "</script>")
    List<SpikeGoodsDTO> page(Page<SpikeGoodsDTO> page, SpikeGoodsDTO spikeGoodsDTO);


    @Select(" select id as goodsId,name as goodsName,goods_sn,price as goodsPrice, stock as goodsStock from goods where id = #{goodsId}")
    SpikeGoodsDTO selectByGoodsId(Long goodsId);

}
