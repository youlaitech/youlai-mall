package com.youlai.mall.ums.convert;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.ums.dto.MemberAuthDTO;
import com.youlai.mall.ums.dto.MemberRegisterDTO;
import com.youlai.mall.ums.model.bo.MemberBO;
import com.youlai.mall.ums.model.entity.UmsMember;
import com.youlai.mall.ums.model.vo.MemberPageVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 *  会员对象转换器
 *
 * @author haoxr
 * @since 2022/6/11
 */
@Mapper(componentModel = "spring")
public interface MemberConvert {
    @Mappings({
            @Mapping(target = "username", source = "openid")
    })
    MemberAuthDTO entity2OpenidAuthDTO(UmsMember entity);

    @Mappings({
            @Mapping(target = "username", source = "mobile")
    })
    MemberAuthDTO entity2MobileAuthDTO(UmsMember entity);

    UmsMember registerDto2Entity(MemberRegisterDTO memberRegisterDTO);


    @Mappings({
            @Mapping(target = "genderLabel", expression = "java(com.youlai.common.base.IBaseEnum.getLabelByValue(bo.getGender(), com.youlai.common.enums.GenderEnum.class))")
    })
    MemberPageVO bo2PageVo(MemberBO bo);
    IPage<MemberPageVO> bo2PageVo(Page<MemberBO> boPage);
}
