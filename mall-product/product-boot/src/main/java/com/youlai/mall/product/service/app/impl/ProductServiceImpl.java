package com.youlai.mall.product.service.app.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.product.mapper.SpuMapper;
import com.youlai.mall.product.model.bo.SkuBO;
import com.youlai.mall.product.model.bo.SkuSpecBO;
import com.youlai.mall.product.model.entity.Spu;
import com.youlai.mall.product.model.query.ProductPageQuery;
import com.youlai.mall.product.model.vo.ProductDetailVO;
import com.youlai.mall.product.model.vo.ProductPageVO;
import com.youlai.mall.product.service.SkuService;
import com.youlai.mall.product.service.app.ProductService;
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
public class ProductServiceImpl extends ServiceImpl<SpuMapper, Spu> implements ProductService {

    private final SkuService skuService;

    /**
     * APP-商品分页列表
     *
     * @param queryParams 查询参数
     * @return 商品分页列表 IPage<SpuPageVO>
     */
    @Override
    public IPage<ProductPageVO> listPagedProducts(ProductPageQuery queryParams) {
        Page<ProductPageVO> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        List<ProductPageVO> list = this.baseMapper.listPagedProducts(page, queryParams);
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
    public ProductDetailVO getProductDetail(Long spuId) {
        ProductDetailVO productDetailVO = new ProductDetailVO();
        // 获取商品基本信息
        Spu spu = this.getById(spuId);
        if (spu != null) {
            // 获取 SKU 列表
            List<SkuBO> skuList = skuService.listSkusBySpuId(spuId);
            // 转换为商品属性列表
            List<ProductDetailVO.Spec> productAttributes = convertToProductAttributes(skuList);
            productDetailVO.setAttributes(productAttributes);
            // 转换为商品 SKU 列表
            List<ProductDetailVO.Sku> productSkus = convertToProductSkus(skuList);
            productDetailVO.setSkuList(productSkus);
            // 获取商品的第一个SKU信息
            ProductDetailVO.Goods goods = getGoodsDetails(skuList, productAttributes);
            if (goods != null) {
                productDetailVO.setGoods(goods);
            }
        }
        return productDetailVO;
    }

    /**
     * 将 SKU 列表转换为商品属性列表
     *
     * @param skuList SKU 列表
     * @return 商品属性列表
     */
    private List<ProductDetailVO.Spec> convertToProductAttributes(List<SkuBO> skuList) {
        Map<Long, ProductDetailVO.Spec> attributeMap = new HashMap<>();
        AtomicBoolean isFirstAttributeValue = new AtomicBoolean(false);

        skuList.forEach(sku -> sku.getSpecValues().forEach(specValue -> {
            Long specId = specValue.getSpecId();
            ProductDetailVO.Spec attribute = attributeMap.computeIfAbsent(specId, id -> {
                ProductDetailVO.Spec attr = new ProductDetailVO.Spec();
                attr.setId(id);
                attr.setName(specValue.getSpecName());
                attr.setList(new ArrayList<>());
                return attr;
            });

            isFirstAttributeValue.set(attribute.getList().isEmpty());
            String attributeValue = specValue.getSpecValue();

            if (attribute.getList().stream().noneMatch(attrValue -> attrValue.getId().equals(attributeValue))) {
                ProductDetailVO.SpecValue attributeValueObj = new ProductDetailVO.SpecValue();
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
    private List<ProductDetailVO.Sku> convertToProductSkus(List<SkuBO> skuList) {
        return skuList.stream().map(sku -> {
            ProductDetailVO.Sku productSku = new ProductDetailVO.Sku();
            productSku.setId(sku.getId());
            productSku.setName(sku.getName());
            productSku.setCode(sku.getCode());
            productSku.setPrice(sku.getPrice());
            productSku.setStock(sku.getStock());
            productSku.setImgUrl(sku.getImgUrl());
            productSku.setSpecValues(sku.getSpecValues().stream()
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
    private ProductDetailVO.Goods getGoodsDetails(List<SkuBO> skuList, List<ProductDetailVO.Spec> productAttributes) {
        List<String> activeAttributeValues = productAttributes.stream()
                .flatMap(attribute -> attribute.getList().stream())
                .filter(ProductDetailVO.SpecValue::getActive)
                .map(ProductDetailVO.SpecValue::getId)
                .toList();

        return skuList.stream()
                .filter(sku -> new HashSet<>(sku.getSpecValues().stream()
                        .map(SkuSpecBO::getSpecValue)
                        .collect(Collectors.toList()))
                        .containsAll(activeAttributeValues))
                .findFirst()
                .map(sku -> {
                    ProductDetailVO.Goods goods = new ProductDetailVO.Goods();
                    goods.setSkuId(sku.getId());
                    goods.setPrice(sku.getPrice());
                    goods.setImagePath(sku.getImgUrl());
                    return goods;
                })
                .orElse(null);
    }

}
