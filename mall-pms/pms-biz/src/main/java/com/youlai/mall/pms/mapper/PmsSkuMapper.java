package com.youlai.mall.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.mall.pms.pojo.PmsSku;
import com.youlai.mall.pms.pojo.vo.SkuInfoVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface PmsSkuMapper extends BaseMapper<PmsSku> {

    @Select("<script>" +
            "  select * from pms_sku where spu_id=#{spuId} " +
            "</script>")
    List<PmsSku> listBySpuId(Long spuId);

    /**
     * 批量获取商品详情
     * @param skuIds 商品id集合
     * @return
     */
    List<SkuInfoVO> getSkuInfoByIds(@Param("skuIds") List<String> skuIds);

    /**
     * 商品锁定库存
     * @param skuId 商品id
     * @param number 要锁定的库存
     * @return
     */
    Long lockStock(@Param("skuId") Long skuId, @Param("number") Integer number);
}
