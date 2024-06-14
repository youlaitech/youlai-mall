package com.youlai.mall.member.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.member.dto.MemberAuthDTO;
import com.youlai.mall.member.dto.MemberRegisterDTO;
import com.youlai.mall.member.model.bo.MemberBO;
import com.youlai.mall.member.model.entity.Member;
import com.youlai.mall.member.model.vo.MemberPageVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 *  会员对象转换器
 *
 * @author Ray
 * @since 2022/6/11
 */
@Mapper(componentModel = "spring")
public interface MemberConvert {
    @Mappings({
            @Mapping(target = "username", source = "openid")
    })
    MemberAuthDTO entity2OpenidAuthDTO(Member entity);

    @Mappings({
            @Mapping(target = "username", source = "mobile")
    })
    MemberAuthDTO entity2MobileAuthDTO(Member entity);

    Member registerDto2Entity(MemberRegisterDTO memberRegisterDTO);


    @Mappings({
            @Mapping(target = "genderLabel", expression = "java(com.youlai.common.base.IBaseEnum.getLabelByValue(bo.getGender(), com.youlai.common.enums.GenderEnum.class))")
    })
    MemberPageVO toPageVo(MemberBO bo);
    Page<MemberPageVO> toPageVo(Page<MemberBO> boPage);
}
