package com.youlai.mall.product.service.client.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.product.converter.SkuConverter;
import com.youlai.mall.product.converter.SpuConverter;
import com.youlai.mall.product.mapper.SpuMapper;
import com.youlai.mall.product.model.bo.SkuBO;
import com.youlai.mall.product.model.entity.SpuEntity;
import com.youlai.mall.product.model.entity.SpuImageEntity;
import com.youlai.mall.product.model.form.SpuForm;
import com.youlai.mall.product.model.query.client.ClientGoodsPageQuery;
import com.youlai.mall.product.model.vo.client.ClientGoodsDetailVO;
import com.youlai.mall.product.model.vo.client.ClientGoodsPageVO;
import com.youlai.mall.product.service.SkuService;
import com.youlai.mall.product.service.SpuAttrService;
import com.youlai.mall.product.service.SpuImageService;
import com.youlai.mall.product.service.client.ClientSpuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 商品服务实现类
 *
 * @author Ray.Hao
 * @since 2024/5/17
 */
@Service
@RequiredArgsConstructor
public class ClientSpuServiceImpl extends ServiceImpl<SpuMapper, SpuEntity> implements ClientSpuService {

  private final SpuConverter spuConverter;
  private final SkuService skuService;
  private final SpuImageService spuImageService;
  private final SpuAttrService spuAttrService;


  /**
   * APP-商品分页列表
   *
   * @param queryParams 查询参数
   * @return 商品分页列表 IPage<SpuPageVO>
   */
  @Override
  public IPage<ClientGoodsPageVO> getGoodsPage(ClientGoodsPageQuery queryParams) {
    Page<ClientGoodsPageVO> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
    List<ClientGoodsPageVO> list = this.baseMapper.getClientGoodsPage(page, queryParams);
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
  public ClientGoodsDetailVO getGoodsDetail(Long spuId) {
    SpuEntity spuEntity = this.getById(spuId);
    if (spuEntity == null) {
      return null;
    }

    ClientGoodsDetailVO goodsDetail = spuConverter.toClientDetailVO(spuEntity);
    // 商品图片
    List<String> imageUrls = spuImageService.list(new LambdaQueryWrapper<SpuImageEntity>()
      .eq(SpuImageEntity::getSpuId, spuId)
      .select(SpuImageEntity::getImgUrl)
    ).stream().map(SpuImageEntity::getImgUrl).toList();
    goodsDetail.setImgList(imageUrls);

    // 属性列表
    List<ClientGoodsDetailVO.AttrValue> attrList = spuAttrService.listAttrsBySpuId(spuId)
      .stream()
      .map(item -> new ClientGoodsDetailVO.AttrValue(item.getAttrName(), item.getAttrValue()))
      .toList();
    goodsDetail.setAttrList(attrList);

    // SKU 列表
    List<ClientGoodsDetailVO.Sku> skuList = skuService.listSkusBySpuId(spuId).stream().map(
      skuBO -> {
        ClientGoodsDetailVO.Sku sku = new ClientGoodsDetailVO.Sku();
        BeanUtil.copyProperties(skuBO, sku);
        // SKU 规格列表
        List<ClientGoodsDetailVO.SpecValue> specList = skuBO.getSpecList()
          .stream()
          .map(specBO -> new ClientGoodsDetailVO.SpecValue(specBO.getSpecName(), specBO.getSpecValue())
          ).toList();
        sku.setSpecList(specList);
        return sku;
      }

    ).toList();
    goodsDetail.setSkuList(skuList);
    return goodsDetail;
  }


}
