package com.fly4j.shop.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fly4j.shop.goods.pojo.entity.Goods;
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
}
