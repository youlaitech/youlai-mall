package com.youlai.mall.pms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.pms.mapper.PmsSpuMapper;
import com.youlai.mall.pms.pojo.bo.app.ProductBO;
import com.youlai.mall.pms.pojo.domain.PmsSpuAttributeValue;
import com.youlai.mall.pms.pojo.domain.PmsSku;
import com.youlai.mall.pms.pojo.domain.PmsSpec;
import com.youlai.mall.pms.pojo.domain.PmsSpu;
import com.youlai.mall.pms.pojo.dto.SpuDTO;
import com.youlai.mall.pms.service.IPmsSpuAttributeValueService;
import com.youlai.mall.pms.service.IPmsSkuService;
import com.youlai.mall.pms.service.IPmsSpecService;
import com.youlai.mall.pms.service.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author haoxr
 * @date 2020-11-06
 */
@Service
@AllArgsConstructor
public class ProductServiceImpl extends ServiceImpl<PmsSpuMapper, PmsSpu> implements IProductService {

    private IPmsSkuService iPmsSkuService;
    private IPmsSpuAttributeValueService iPmsSpuAttributeValueService;
    private IPmsSpecService iPmsSpecService;

    @Override
    public ProductBO getProductById(Long spuId) {
        // spu
        PmsSpu spu = this.getById(spuId);
        SpuDTO SpuDTO = new SpuDTO();
        BeanUtil.copyProperties(spu, SpuDTO);
        if (StrUtil.isNotBlank(spu.getPics())) {
            // spu专辑图片转换处理 json字符串 -> List
            List<String> pics = JSONUtil.toList(JSONUtil.parseArray(spu.getPics()), String.class);
            SpuDTO.setPics(pics);
        }
        // 属性
        List<PmsSpuAttributeValue> attrs = iPmsSpuAttributeValueService.list(
                new LambdaQueryWrapper<PmsSpuAttributeValue>(
                ).eq(PmsSpuAttributeValue::getSpuId, spuId)
        );

        // 规格
        List<PmsSpec> specs = iPmsSpecService.listBySpuId(spuId);

        // sku
        List<PmsSku> skuList = iPmsSkuService.list(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, spuId));

        ProductBO product = new ProductBO(SpuDTO, attrs, specs, skuList);
        return product;
    }
}
