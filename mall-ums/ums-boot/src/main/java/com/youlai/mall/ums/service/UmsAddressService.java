package com.youlai.mall.ums.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.ums.dto.MemberAddressDTO;
import com.youlai.mall.ums.model.entity.UmsAddress;
import com.youlai.mall.ums.model.form.AddressForm;

import java.util.List;

/**
 * 会员地址业务接口
 *
 * @author haoxr
 * @since 2022/2/12
 */
public interface UmsAddressService extends IService<UmsAddress> {

    /**
     * 新增地址
     *
     * @param addressForm
     * @return
     */
    boolean addAddress(AddressForm addressForm);

    /**
     * 修改地址
     *
     * @param addressForm
     * @return
     */
    boolean updateAddress(AddressForm addressForm);

    /**
     * 获取当前登录会员的地址列表
     *
     * @return
     */
    List<MemberAddressDTO> listCurrentMemberAddresses();
}
