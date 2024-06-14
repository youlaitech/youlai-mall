package com.youlai.mall.member.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.constant.GlobalConstants;
import com.youlai.common.security.util.SecurityUtils;
import com.youlai.mall.member.dto.MemberAddressDTO;
import com.youlai.mall.member.mapper.AddressMapper;
import com.youlai.mall.member.model.entity.Address;
import com.youlai.mall.member.model.form.AddressForm;
import com.youlai.mall.member.service.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 会员地址业务实现类
 *
 * @author Ray
 * @since 2022/2/12
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

    /**
     * 新增地址
     *
     * @param addressForm
     * @return
     */
    @Override
    @Transactional
    public boolean addAddress(AddressForm addressForm) {
        Long memberId = SecurityUtils.getMemberId();

        Address address = new Address();
        BeanUtil.copyProperties(addressForm, address);
        address.setMemberId(memberId);
        boolean result = this.save(address);
        if (result) {
            // 修改其他默认地址为非默认
            if (GlobalConstants.STATUS_YES.equals(addressForm.getIsDefault())) {
                this.update(new LambdaUpdateWrapper<Address>()
                        .eq(Address::getMemberId, memberId)
                        .eq(Address::getIsDefault, 1)
                        .ne(Address::getId, address.getId())
                        .set(Address::getIsDefault, 0)
                );
            }
        }
        return result;
    }

    /**
     * 修改地址
     *
     * @param addressForm
     * @return
     */
    @Override
    public boolean updateAddress(AddressForm addressForm) {
        Long memberId = SecurityUtils.getMemberId();

        Address address = new Address();
        BeanUtil.copyProperties(addressForm, address);

        boolean result = this.updateById(address);

        if(result){
            // 修改其他默认地址为非默认
            if (GlobalConstants.STATUS_YES.equals(addressForm.getIsDefault())) {
                this.update(new LambdaUpdateWrapper<Address>()
                        .eq(Address::getMemberId, memberId)
                        .eq(Address::getIsDefault, 1)
                        .ne(Address::getId, address.getId())
                        .set(Address::getIsDefault, 0)
                );
            }
        }
        return result;
    }

    /**
     * 获取当前登录会员的地址列表
     *
     * @return
     */
    @Override
    public List<MemberAddressDTO> listCurrentMemberAddresses() {
        Long memberId = SecurityUtils.getMemberId();
        List<Address> addressList = this.list(new LambdaQueryWrapper<Address>()
                .eq(Address::getMemberId, memberId)
                .orderByDesc(Address::getIsDefault) // 默认地址排在首位
        );
        List<MemberAddressDTO> memberAddressList = Optional.ofNullable(addressList).orElse(new ArrayList<>()).stream()
                .map(umsAddress -> {
                    MemberAddressDTO memberAddressDTO = new MemberAddressDTO();
                    BeanUtil.copyProperties(umsAddress, memberAddressDTO);
                    return memberAddressDTO;
                }).collect(Collectors.toList());
        return memberAddressList;
    }
}
