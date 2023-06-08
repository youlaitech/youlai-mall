package com.youlai.mall.ums.convert;

import com.youlai.mall.ums.dto.MemberAuthDTO;
import com.youlai.mall.ums.dto.MemberInfoDTO;
import com.youlai.mall.ums.dto.MemberRegisterDTO;
import com.youlai.mall.ums.pojo.entity.UmsMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 *  会员对象转换器
 *
 * @author haoxr
 * @since  2022/6/11
 */
@Mapper(componentModel = "spring")
public interface MemberConvert {
    @Mappings({
            @Mapping(target = "memberId", source = "id"),
            @Mapping(target = "username", source = "openid")
    })
    MemberAuthDTO entity2OpenidAuthDTO(UmsMember entity);

    @Mappings({
            @Mapping(target = "memberId", source = "id"),
            @Mapping(target = "username", source = "mobile")
    })
    MemberAuthDTO entity2MobileAuthDTO(UmsMember entity);

    MemberInfoDTO entity2MemberInfoDTO(UmsMember entity);

    UmsMember dto2Entity(MemberRegisterDTO memberDTO);
}
