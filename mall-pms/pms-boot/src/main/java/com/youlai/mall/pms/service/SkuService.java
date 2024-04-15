package com.youlai.mall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.model.dto.LockSkuDTO;
import com.youlai.mall.pms.model.dto.SkuInfoDto;
import com.youlai.mall.pms.model.entity.Sku;

import java.util.List;


/**
 * 商品库存接口
 *
 * @author haoxr
 * @since 2.0.0
 */
public interface SkuService extends IService<Sku> {

    /**
     * 获取商品库存信息
     *
     * @param skuId SKU ID
     * @return 商品库存信息
     */
    SkuInfoDto getSkuInfo(Long skuId);

    /**
     * 获取商品库存信息列表
     *
     * @param skuIds SKU ID 列表
     * @return 商品库存信息列表
     */
    List<SkuInfoDto> listSkuInfos(List<Long> skuIds);

    /**
     * 校验并锁定库存
     *
     * @param orderToken 订单临时编号 (此时订单未创建)
     * @param lockSkuList 锁定商品库存信息列表
     * @return true/false
     */
    boolean lockStock(String orderToken,List<LockSkuDTO> lockSkuList);

    /**
     * 解锁库存
     */
    boolean unlockStock(String orderSn);

    /**
     * 扣减库存
     */
    boolean deductStock(String orderSn);


}
