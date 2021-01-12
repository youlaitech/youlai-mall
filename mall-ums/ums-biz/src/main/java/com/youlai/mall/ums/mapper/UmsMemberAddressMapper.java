package com.youlai.mall.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.mall.ums.pojo.UmsMember;
import com.youlai.mall.ums.pojo.UmsMemberAddress;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UmsMemberAddressMapper extends BaseMapper<UmsMember> {


    @Select("<script>" +
            "  select * from ums_member_address where member_id=#{memberId} " +
            "</script>")
    List<UmsMemberAddress> listByMemberId(Long memberId);


}
