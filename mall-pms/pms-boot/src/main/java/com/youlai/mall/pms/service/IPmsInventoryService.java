package com.youlai.mall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.pojo.domain.PmsInventory;
import com.youlai.mall.pms.pojo.vo.SkuInfoVO;
import com.youlai.mall.pms.pojo.vo.WareSkuStockVO;

import java.util.List;

public interface IPmsInventoryService extends IService<PmsInventory> {

    /**
     * 批量获取商品详情
     * @param skuIds
     * @return
     */
    List<SkuInfoVO> getSkuInfoByIds(List<String> skuIds);

    /**
     * 订单下单锁定库存
     * @param skuStockVO 商品锁定库存实体类
     * @return 库存锁定结果
     */
    boolean lockStock(WareSkuStockVO skuStockVO);

    /**
     * 订单关闭释放库存
     * @param skuStockVO 商品库存实体类
     * @return 释放库存结果
     */
    boolean releaseStock(WareSkuStockVO skuStockVO);


    /**
     * 获取商品库存
     * @param id 库存ID
     * @return
     */
    Integer getInventoryById(Long id);
}
