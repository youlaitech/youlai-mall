package com.youlai.mall.ums.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.ums.dto.MemberAddressDTO;
import com.youlai.mall.ums.pojo.entity.UmsAddress;

import java.util.List;

/**
 * 会员地址业务接口
 *
 * @author haoxr
 * @date 2022/2/12
 */
public interface IUmsAddressService extends IService<UmsAddress> {

    /**
     * 添加地址
     *
     * @param address
     * @return
     */
    boolean addAddress(UmsAddress address);

    /**
     * 修改地址
     *
     * @param address
     * @return
     */
    boolean updateAddress(UmsAddress address);

    /**
     * 获取当前登录会员的地址列表
     *
     * @return
     */
    List<MemberAddressDTO> listCurrentMemberAddresses();
}
