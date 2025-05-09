package com.youlai.mall.product.service.client.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.product.mapper.SpuMapper;
import com.youlai.mall.product.model.bo.SkuBO;
import com.youlai.mall.product.model.bo.SkuSpecBO;
import com.youlai.mall.product.model.entity.SpuEntity;
import com.youlai.mall.product.model.query.client.ClientGoodsPageQuery;
import com.youlai.mall.product.model.vo.client.ClientSpuDetailVO;
import com.youlai.mall.product.model.vo.client.ClientSpuPageVO;
import com.youlai.mall.product.service.SkuService;
import com.youlai.mall.product.service.client.ClientSpuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * 商品服务实现类
 *
 * @author Ray.Hao
 * @since 2024/5/17
 */
@Service
@RequiredArgsConstructor
public class ClientSpuServiceImpl extends ServiceImpl<SpuMapper, SpuEntity>  implements ClientSpuService {

    private final SkuService skuService;

    /**
     * APP-商品分页列表
     *
     * @param queryParams 查询参数
     * @return 商品分页列表 IPage<SpuPageVO>
     */
    @Override
    public IPage<ClientSpuPageVO> getSpuPage(ClientGoodsPageQuery queryParams) {
        Page<ClientSpuPageVO> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        List<ClientSpuPageVO> list = this.baseMapper.getClientSpuPage(page, queryParams);
        page.setRecords(list);
        return page;
    }

    /**
     * APP-获取商品详情
     *
     * @param spuId SPU ID
     * @return 商品详情 ProductDetailVO
     */
    @Override
    public ClientSpuDetailVO getSpuDetail(Long spuId) {
        ClientSpuDetailVO clientSpuDetailVO = new ClientSpuDetailVO();
        // 获取商品基本信息
        SpuEntity spuEntity = this.getById(spuId);
        if (spuEntity != null) {
            // 获取 SKU 列表
            List<SkuBO> skuList = skuService.listSkusBySpuId(spuId);
            // 转换为商品属性列表
            List<ClientSpuDetailVO.Spec> productAttributes = convertToProductAttributes(skuList);
            clientSpuDetailVO.setAttributes(productAttributes);
            // 转换为商品 SKU 列表
            List<ClientSpuDetailVO.Sku> productSkus = convertToProductSkus(skuList);
            clientSpuDetailVO.setSkuList(productSkus);
            // 获取商品的第一个SKU信息
            ClientSpuDetailVO.Goods goods = getGoodsDetails(skuList, productAttributes);
            if (goods != null) {
                clientSpuDetailVO.setGoods(goods);
            }
        }
        return clientSpuDetailVO;
    }

    /**
     * 将 SKU 列表转换为商品属性列表
     *
     * @param skuList SKU 列表
     * @return 商品属性列表
     */
    private List<ClientSpuDetailVO.Spec> convertToProductAttributes(List<SkuBO> skuList) {
        Map<Long, ClientSpuDetailVO.Spec> attributeMap = new HashMap<>();
        AtomicBoolean isFirstAttributeValue = new AtomicBoolean(false);

        skuList.forEach(sku -> sku.getSpecList().forEach(specValue -> {
            Long specId = specValue.getSpecId();
            ClientSpuDetailVO.Spec attribute = attributeMap.computeIfAbsent(specId, id -> {
                ClientSpuDetailVO.Spec attr = new ClientSpuDetailVO.Spec();
                attr.setId(id);
                attr.setName(specValue.getSpecName());
                attr.setList(new ArrayList<>());
                return attr;
            });

            isFirstAttributeValue.set(attribute.getList().isEmpty());
            String attributeValue = specValue.getSpecValue();

            if (attribute.getList().stream().noneMatch(attrValue -> attrValue.getId().equals(attributeValue))) {
                ClientSpuDetailVO.SpecValue attributeValueObj = new ClientSpuDetailVO.SpecValue();
                attributeValueObj.setId(attributeValue);
                attributeValueObj.setName(attributeValue);
                attributeValueObj.setActive(isFirstAttributeValue.get()); // 设置激活状态
                attributeValueObj.setDisable(false); // 设置禁用状态为 false
                attribute.getList().add(attributeValueObj);
            }
        }));

        return new ArrayList<>(attributeMap.values());
    }


    /**
     * 转换 SKU 列表为商品 SKU 列表
     *
     * @param skuList SKU 列表
     * @return 商品 SKU 列表
     */
    private List<ClientSpuDetailVO.Sku> convertToProductSkus(List<SkuBO> skuList) {
        return skuList.stream().map(sku -> {
            ClientSpuDetailVO.Sku productSku = new ClientSpuDetailVO.Sku();
            productSku.setId(sku.getId());
            productSku.setPrice(sku.getPrice());
            productSku.setStock(sku.getStock());
            productSku.setImgUrl(sku.getImgUrl());
            productSku.setSpecValues(sku.getSpecList().stream()
                    .map(SkuSpecBO::getSpecValue)
                    .collect(Collectors.toList()));
            return productSku;
        }).toList();
    }

    /**
     * 获取商品详情，包括 SKU ID、价格和图片路径
     *
     * @param skuList           SKU 列表
     * @param productAttributes 商品属性列表
     * @return 商品详情
     */
    private ClientSpuDetailVO.Goods getGoodsDetails(List<SkuBO> skuList, List<ClientSpuDetailVO.Spec> productAttributes) {
        List<String> activeAttributeValues = productAttributes.stream()
                .flatMap(attribute -> attribute.getList().stream())
                .filter(ClientSpuDetailVO.SpecValue::getActive)
                .map(ClientSpuDetailVO.SpecValue::getId)
                .toList();

        return skuList.stream()
                .filter(sku -> new HashSet<>(sku.getSpecList().stream()
                        .map(SkuSpecBO::getSpecValue)
                        .collect(Collectors.toList()))
                        .containsAll(activeAttributeValues))
                .findFirst()
                .map(sku -> {
                    ClientSpuDetailVO.Goods goods = new ClientSpuDetailVO.Goods();
                    goods.setSkuId(sku.getId());
                    goods.setPrice(sku.getPrice());
                    goods.setImagePath(sku.getImgUrl());
                    return goods;
                })
                .orElse(null);
    }

}
