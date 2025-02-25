package com.youlai.mall.member.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.member.dto.MemberAddressDTO;
import com.youlai.mall.member.model.entity.Address;
import com.youlai.mall.member.model.form.AddressForm;

import java.util.List;

/**
 * 会员地址业务接口
 *
 * @author Ray.Hao
 * @since 2022/2/12
 */
public interface AddressService extends IService<Address> {

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
