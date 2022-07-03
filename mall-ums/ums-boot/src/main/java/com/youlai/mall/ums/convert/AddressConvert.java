package com.youlai.mall.ums.convert;

import com.youlai.mall.ums.dto.MemberAddressDTO;
import com.youlai.mall.ums.pojo.entity.UmsAddress;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 会员地址对象转换器
 *
 * @author haoxr
 * @date 2022/6/21 23:52
 */
@Mapper(componentModel = "spring")
public interface AddressConvert {

    MemberAddressDTO entity2Dto(UmsAddress entity);

    List<MemberAddressDTO> entity2Dto(List<UmsAddress> entities);
}
