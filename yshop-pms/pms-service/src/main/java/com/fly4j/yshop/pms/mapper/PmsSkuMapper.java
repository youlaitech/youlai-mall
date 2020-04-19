package com.fly4j.yshop.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fly4j.yshop.pms.pojo.entity.PmsSku;
import com.fly4j.yshop.pms.pojo.vo.SkuLockVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PmsSkuMapper extends BaseMapper<PmsSku> {

    @Select("<script>" +
            "SELECT id AS sku_id,stock_locked AS quantity FROM pms_sku  WHERE id = #{sku_id} AND stock-stock_locked >= #{quantity}" +
            "</script>")
    SkuLockVO getCanLocked(SkuLockVO skuLockVO);

    @Update("<script>" +
            "UPDATE pms_sku SET stock=stock-#{quantity}, stock_locked = stock_locked + #{quantity} WHERE id = #{sku_id}" +
            "</script>")
    long lockSku(@Param("sku_id") Long sku_id, @Param("quantity")Integer quantity);

    @Update("<script>" +
            "UPDATE pms_sku SET  stock=stock+#{quantity},stock_locked = stock_locked-#{quantity} WHERE id = #{sku_id}" +
            "</script>")
    void unLockSku(SkuLockVO skuLock);

    @Update("<script>" +
            "UPDATE pms_sku SET stock = stock - #{sku_quantity} AND stock_locked = 0 WHERE id = #{sku_id}" +
            "</script>")
    Integer minusStock(Long sku_id, Integer sku_quantity);
}
