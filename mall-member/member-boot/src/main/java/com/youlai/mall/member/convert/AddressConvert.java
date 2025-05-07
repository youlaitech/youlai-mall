package com.youlai.mall.member.convert;

import com.youlai.mall.member.dto.MemberAddressDTO;
import com.youlai.mall.member.model.entity.AddressEntity;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 会员地址对象转换器
 *
 * @author Ray.Hao
 * @since 2022/6/21 23:52
 */
@Mapper(componentModel = "spring")
public interface AddressConvert {

    MemberAddressDTO entity2Dto(AddressEntity entity);

    List<MemberAddressDTO> entity2Dto(List<AddressEntity> entities);
}
