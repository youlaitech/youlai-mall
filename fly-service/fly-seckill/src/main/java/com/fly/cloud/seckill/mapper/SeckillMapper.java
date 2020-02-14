package com.fly.cloud.seckill.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fly.cloud.seckill.pojo.entity.Seckill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

@Mapper
public interface SeckillMapper extends BaseMapper<Seckill> {
    /**
     * 减库存
     *
     * @param seckillId 秒杀商品ID
     * @param killTime  秒杀时间
     * @return 返回此SQL更新的记录数，如果>=1表示更新成功
     */
    @Update("<script>" +
            "UPDATE seckill" +
            "   SET stock_count = stock_count - 1" +
            "   WHERE seckill_id = #{seckillId}" +
            "   AND start_time &lt;= #{killTime}" +
            "   AND end_time &gt;= #{killTime}" +
            "   AND stock_count &gt; 0" +
            "</script>")
    int reduceStock(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * 根据秒杀id查询商品信息
     * @param seckillId 秒杀商品ID
     * @return
     */
    @Select("<script>" +
            "select * from seckill where seckill_id = #{seckillId}" +
            "</script>")
    Seckill findById(@Param("seckillId") long seckillId);

    /**
     * 查询所有秒杀商品信息
     * @return
     */
    @Select("<script>" +
            "select * from seckill" +
            "</script>")
    List<Seckill> findAll();


}
