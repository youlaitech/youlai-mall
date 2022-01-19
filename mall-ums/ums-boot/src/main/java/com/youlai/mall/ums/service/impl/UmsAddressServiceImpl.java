package com.youlai.mall.ums.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.constant.GlobalConstants;
import com.youlai.common.web.util.JwtUtils;
import com.youlai.mall.ums.mapper.UmsAddressMapper;
import com.youlai.mall.ums.pojo.entity.UmsAddress;
import com.youlai.mall.ums.service.IUmsAddressService;
import org.springframework.stereotype.Service;

@Service
public class UmsAddressServiceImpl extends ServiceImpl<UmsAddressMapper, UmsAddress> implements IUmsAddressService {


    /**
     * 添加地址
     *
     * @param address
     * @return
     */
    @Override
    public boolean addAddress(UmsAddress address) {
        Long memberId = JwtUtils.getUserId();
        address.setMemberId(memberId);
        if (GlobalConstants.STATUS_YES.equals(address.getDefaulted())) { // 修改其他默认地址为非默认
            this.update(new LambdaUpdateWrapper<UmsAddress>()
                    .eq(UmsAddress::getMemberId, memberId)
                    .eq(UmsAddress::getDefaulted, 1)
                    .set(UmsAddress::getDefaulted, 0)
            );
        }
        return this.save(address);
    }


    /**
     * 修改地址
     *
     * @param address
     * @return
     */
    @Override
    public boolean updateAddress(UmsAddress address) {
        Long loginUserId = JwtUtils.getUserId();
        // 修改其他默认地址为非默认
        if (GlobalConstants.STATUS_YES.equals(address.getDefaulted())) {
            this.update(new LambdaUpdateWrapper<UmsAddress>()
                    .eq(UmsAddress::getMemberId, loginUserId)
                    .eq(UmsAddress::getDefaulted, 1)
                    .set(UmsAddress::getDefaulted, 0)
            );
        }
        return this.updateById(address);
    }

}
