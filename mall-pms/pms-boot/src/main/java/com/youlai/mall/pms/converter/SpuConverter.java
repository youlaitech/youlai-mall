package com.youlai.mall.pms.converter;

import com.youlai.mall.pms.model.entity.Spu;
import com.youlai.mall.pms.model.form.SpuForm;
import com.youlai.mall.pms.model.vo.SeckillingSpuVO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 商品对象转换器
 *
 * @author Ray Hao
 * @since 2024/4/24
 */
@Mapper(componentModel = "spring")
public interface SpuConverter {

    Spu form2Entity(SpuForm form);

    @InheritInverseConfiguration(name="form2Entity")
    SpuForm entity2Form(Spu entity);

    SeckillingSpuVO entity2SeckillingVO(Spu entity);

    List<SeckillingSpuVO> entity2SeckillingVO(List<Spu> entities);

}
