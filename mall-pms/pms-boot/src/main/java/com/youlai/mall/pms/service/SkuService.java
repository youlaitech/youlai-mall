package com.youlai.mall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.model.dto.LockedSkuDTO;
import com.youlai.mall.pms.model.dto.SkuInfoDTO;
import com.youlai.mall.pms.model.entity.PmsSku;

import java.util.List;


/**
 * 商品库存接口
 *
 * @author haoxr
 * @since 2.0.0
 */
public interface SkuService extends IService<PmsSku> {

    /**
     * 获取商品库存信息
     *
     * @param skuId SKU ID
     * @return 商品库存信息
     */
    SkuInfoDTO getSkuInfo(Long skuId);

    /**
     * 获取商品库存信息列表
     *
     * @param skuIds SKU ID 列表
     * @return 商品库存信息列表
     */
    List<SkuInfoDTO> listSkuInfos(List<Long> skuIds);

    /**
     * 校验并锁定库存
     *
     * @param orderToken 订单临时编号 (此时订单未创建)
     * @param lockedSkuList 锁定商品库存信息列表
     * @return true/false
     */
    boolean lockStock(String orderToken,List<LockedSkuDTO> lockedSkuList);

    /**
     * 解锁库存
     */
    boolean unlockStock(String orderSn);

    /**
     * 扣减库存
     */
    boolean deductStock(String orderSn);


}
