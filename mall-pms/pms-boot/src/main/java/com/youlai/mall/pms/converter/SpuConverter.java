package com.youlai.mall.pms.converter;

import com.youlai.mall.pms.model.entity.Spu;
import com.youlai.mall.pms.model.form.PmsSpuForm;
import com.youlai.mall.pms.model.vo.SeckillingSpuVO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * 商品对象转换器
 *
 * @author haoxr
 * @date 2022/6/11
 */
@Mapper(componentModel = "spring")
public interface SpuConverter {

    @Mappings({
            @Mapping(target = "album", source = "subImgUrls")
    })
    Spu form2Entity(PmsSpuForm form);

    @InheritInverseConfiguration(name="form2Entity")
    PmsSpuForm entity2Form(Spu entity);

    SeckillingSpuVO entity2SeckillingVO(Spu entity);

    List<SeckillingSpuVO> entity2SeckillingVO(List<Spu> entities);

}
