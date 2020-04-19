package com.fly4j.yshop.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.yshop.pms.pojo.dto.PmsSkuDTO;
import com.fly4j.yshop.pms.pojo.entity.PmsSku;
import org.apache.ibatis.annotations.Select;
import com.fly4j.yshop.pms.pojo.vo.SkuLockVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

public interface PmsSkuMapper extends BaseMapper<PmsSku> {

    @Select("<script>" +
            "SELECT id AS sku_id,stock_locked AS quantity FROM pms_sku  WHERE id = #{sku_id} AND stock-stock_locked >= #{quantity}" +
            "</script>")
    SkuLockVO getCanLocked(SkuLockVO skuLockVO);

    @Update("<script>" +
            "UPDATE pms_sku SET stock=stock-#{quantity}, stock_locked = stock_locked + #{quantity} WHERE id = #{sku_id}" +
            "</script>")
    long lockSku(@Param("sku_id") Long sku_id, @Param("quantity")Integer quantity);

    @Select("<script>"
                 +" SELECT"
                 +"     a.id,"
                 +"     a.specs,"
                 +"     a.price,"
                 +"     a.pic_url,"
                 +"     ( a.stock - a.stock_locked ) AS stock,"
                 +"     b.name AS spu_name,"
                 +"     b.code AS spu_code"
                 +" FROM"
                 +" pms_sku a"
                 +" LEFT JOIN pms_spu b ON a.spu_id = b.id"
                 +" ORDER BY a.create_time"
                 +"</script>")
    Page<PmsSkuDTO> page(Map<String, Object> params, Page<PmsSku> page);

    @Update("<script>" +
            "UPDATE pms_sku SET  stock=stock+#{quantity},stock_locked = stock_locked-#{quantity} WHERE id = #{sku_id}" +
            "</script>")
    void unLockSku(SkuLockVO skuLock);

    @Update("<script>" +
            "UPDATE pms_sku SET stock = stock - #{sku_quantity} AND stock_locked = 0 WHERE id = #{sku_id}" +
            "</script>")
    Integer minusStock(Long sku_id, Integer sku_quantity);
}
