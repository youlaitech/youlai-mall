package com.youlai.mall.product.mapper;

import com.youlai.mall.product.model.entity.SkuSpec;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * SKU 规格持久层接口
 *
 * @author Ray.Hao
 * @since 2024/04/14
 */

@Mapper
public interface SkuSpecMapper extends BaseMapper<SkuSpec> {

    /**
     * 根据SKU ID获取销售属性值列表
     *
     * @param skuId SKU ID
     * @return 销售属性值列表
     */
    List<SkuSpec> listSpecBySkuId(Long skuId);

}
