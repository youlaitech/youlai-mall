package com.fly4j.shop.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.shop.goods.pojo.dto.SeckillGoodsDTO;
import com.fly4j.shop.goods.pojo.entity.Goods;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
            " select id,name as goods_name,goods_sn,price as goods_price, stock as goods_stock from goods " +
            "  where 1=1 " +
            "   <if test='seckillGoodsDTO.goodsName !=null and seckillGoodsDTO.goodsName.trim() neq \"\"'>" +
            "        and name like concat('%',#{seckillGoodsDTO.goodsName},'%') " +
            "   </if> " +
            " order by sort asc" +
            "</script>")
    Page<SeckillGoodsDTO> page(Page<Goods> page, SeckillGoodsDTO seckillGoodsDTO);


    @Select(" select id,name as goods_name,goods_sn,price as goods_price, stock as goods_stock from goods where id = #{goodsId}")
    SeckillGoodsDTO selectByGoodsId(Long goodsId);

}
