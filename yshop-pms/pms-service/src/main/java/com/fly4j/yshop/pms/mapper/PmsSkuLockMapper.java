package com.fly4j.yshop.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fly4j.yshop.pms.pojo.entity.PmsSkuLock;
import com.fly4j.yshop.pms.pojo.vo.SkuLockVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface PmsSkuLockMapper extends BaseMapper<PmsSkuLock> {

    @Select("<script>" +
            "SELECT * FROM pms_sku_lock WHERE sku_id = #{sku_id} AND stock-stock_locked >= #{quantity}" +
            "</script>")
    List<PmsSkuLock> getAllCanLocked(SkuLockVO skuLockVO);

    @Update("<script>" +
            "UPDATE pms_sku_lock SET stock_locked = stock_locked + #{quantity} WHERE id = #{id}" +
            "</script>")
    long lockSku(@Param("id") Long id, @Param("quantity")Integer quantity);

    @Update("<script>" +
            "UPDATE pms_sku_lock SET stock_locked = stock_locked - #{locked} WHERE id = #{sku_id}" +
            "</script>")
    void unLockSku(SkuLockVO skuLock);
}
