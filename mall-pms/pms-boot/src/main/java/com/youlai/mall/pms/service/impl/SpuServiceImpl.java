package com.youlai.mall.pms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.pms.mapper.PmsProductMapper;
import com.youlai.mall.pms.pojo.bo.app.ProductBO;
import com.youlai.mall.pms.pojo.domain.PmsAttributeValue;
import com.youlai.mall.pms.pojo.domain.PmsSku;
import com.youlai.mall.pms.pojo.domain.PmsSpecification;
import com.youlai.mall.pms.pojo.domain.PmsSpu;
import com.youlai.mall.pms.pojo.dto.SpuDTO;
import com.youlai.mall.pms.service.IPmsAttributeValueService;
import com.youlai.mall.pms.service.IPmsSkuService;
import com.youlai.mall.pms.service.IPmsSpecificationService;
import com.youlai.mall.pms.service.ISpuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author haoxr
 * @date 2020-11-06
 */
@Service
@AllArgsConstructor
public class SpuServiceImpl extends ServiceImpl<PmsProductMapper, PmsSpu> implements ISpuService {

    private IPmsSkuService iPmsSkuService;
    private IPmsAttributeValueService iPmsAttributeValueService;
    private IPmsSpecificationService iPmsSpecificationService;

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
        List<PmsAttributeValue> attrs = iPmsAttributeValueService.list(
                new LambdaQueryWrapper<PmsAttributeValue>(
                ).eq(PmsAttributeValue::getSpuId, spuId)
        );

        // 规格
        List<PmsSpecification> specs = iPmsSpecificationService.listBySpuId(spuId);

        // sku
        List<PmsSku> skuList = iPmsSkuService.list(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, spuId));

        ProductBO product = new ProductBO(SpuDTO, attrs, specs, skuList);
        return product;
    }
}
