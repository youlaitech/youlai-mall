package com.youlai.mall.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.mall.ums.model.bo.AddressBO;
import com.youlai.mall.ums.model.entity.UmsAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 会员地址访问层接口
 *
 * @author Ray Hao
 * @since 2024/4/8
 */
@Mapper
public interface UmsAddressMapper extends BaseMapper<UmsAddress> {


    /**
     * 根据会员ID获取地址列表
     *
     * @param memberId 会员ID
     * @return 会员址列表
     */
    List<AddressBO>  listAddressesByMemberId(Long memberId);

}
