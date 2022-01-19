package com.youlai.mall.ums.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.ums.pojo.entity.UmsAddress;

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


}
