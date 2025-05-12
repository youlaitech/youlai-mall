package com.youlai.mall.product.converter;

import com.youlai.mall.product.model.entity.SpuEntity;
import com.youlai.mall.product.model.form.SpuForm;
import com.youlai.mall.product.model.vo.client.ClientGoodsDetailVO;
import org.mapstruct.Mapper;

/**
 * 商品对象转换器
 *
 * @author Ray.Hao
 * @since 2024/4/24
 */
@Mapper(componentModel = "spring")
public interface SpuConverter {

  SpuEntity toEntity(SpuForm form);

  SpuForm toForm(SpuEntity entity);

  ClientGoodsDetailVO toClientDetailVO(SpuEntity entity);
}
