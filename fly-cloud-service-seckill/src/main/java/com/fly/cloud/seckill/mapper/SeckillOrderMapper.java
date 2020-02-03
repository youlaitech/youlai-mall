package com.fly.cloud.seckill.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fly.cloud.seckill.pojo.entity.Seckill;
import com.fly.cloud.seckill.pojo.entity.SeckillOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

@Mapper
public interface SeckillOrderMapper extends BaseMapper<Seckill> {

    /**
     * 插入购买订单明细
     *
     * @param seckillId 秒杀到的商品ID
     * @param money     秒杀的金额
     * @param userPhone 秒杀的用户
     * @return 返回该SQL更新的记录数，如果>=1则更新成功
     *
     * 如果同一个用户多次抢购同一件商品导致主键冲突会直接报错，为了避免系统不直接报错设计了ignore实现主键冲突就直接返回0表示该条SQL执行失败
     */
    @Insert("<script>" +
            "INSERT ignore INTO seckill_order(seckill_id, money, user_phone)" +
            "   VALUES (#{seckillId}, #{money}, #{userPhone})" +
            "</script>")
    int insertOrder(@Param("seckillId") long seckillId,@Param("money") BigDecimal money,@Param("userPhone") long userPhone);

    /**
     * 根据秒杀商品ID查询订单明细数据并得到对应秒杀商品的数据，因为我们再SeckillOrder中已经定义了一个Seckill的属性
     *
     * @param seckillId
     * @return
     */

    @Select("<script>" +
            "  SELECT" +
            "   so.seckill_id," +
            "   so.user_phone," +
            "   so.money," +
            "   so.create_time," +
            "   so.state," +
            "   s.seckill_id 'seckill.seckill_id'," +
            "   s.title 'seckill.title'," +
            "   s.cost_price 'seckill.cost_price'," +
            "   s.create_time 'seckill.create_time'," +
            "   s.start_time 'seckill.start_time'," +
            "   s.end_time 'seckill.end_time'," +
            "   s.stock_count 'seckill.stock_count'  " +
            " FROM seckill_order so" +
            "    INNER JOIN seckill s ON so.seckill_id = s.seckill_id" +
            "    WHERE so.seckill_id = #{seckillId}" +
            "</script>")
    SeckillOrder findById(@Param("seckillId") long seckillId);
}

