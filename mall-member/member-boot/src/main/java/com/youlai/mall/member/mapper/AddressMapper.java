package com.youlai.mall.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.mall.member.model.bo.AddressBO;
import com.youlai.mall.member.model.entity.Address;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 会员地址访问层接口
 *
 * @author Ray Hao
 * @since 2024/4/8
 */
@Mapper
public interface AddressMapper extends BaseMapper<Address> {


    /**
     * 根据会员ID获取地址列表
     *
     * @param memberId 会员ID
     * @return 会员址列表
     */
    List<AddressBO>  listAddressesByMemberId(Long memberId);

}
