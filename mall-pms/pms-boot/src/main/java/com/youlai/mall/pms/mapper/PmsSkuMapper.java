package com.youlai.mall.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.mall.pms.pojo.domain.PmsSku;
import com.youlai.mall.pms.pojo.dto.SkuDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PmsSkuMapper extends BaseMapper<PmsSku> {

    @Select("<script>" +
            "  select * from pms_sku where spu_id=#{spuId}" +
            "</script>")
    List<PmsSku> listBySpuId(Long spuId);


    @Select("<script>" +
            "  select t1.id,t1.code,t1.name,t1.pic,t1.price,(t1.stock-t1.locked_stock) as stock,t2.name as spu_name from pms_sku t1" +
            "  left join pms_spu t2 on t1.spu_id=t2.id" +
            "  where t1.id=#{id}" +
            "</script>")
    SkuDTO getSkuById(Long id);
}
