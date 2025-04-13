package com.youlai.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.product.model.bo.SkuBO;
import com.youlai.mall.product.model.dto.LockSkuDTO;
import com.youlai.mall.product.model.dto.SkuDTO;
import com.youlai.mall.product.model.entity.SkuEntity;
import com.youlai.mall.product.model.form.SpuForm;

import java.util.List;

/**
 * SKU 接口
 *
 * @author Ray.Hao
 * @since 2.0.0
 */
public interface SkuService extends IService<SkuEntity> {

    /**
     * 获取 SKU 传输对象
     *
     * @param skuId SKU ID
     * @return SKU 传输对象
     */
    SkuDTO getSkuInfo(Long skuId);


    /**
     * 获取 SKU 业务对象
     *
     * @param skuId SKU ID
     * @return SKU 业务对象
     */
    SkuBO getSkuById(Long skuId);

    /**
     * 获取商品库存信息列表
     *
     * @param skuIds SKU ID 列表
     * @return 商品库存信息列表
     */
    List<SkuDTO> listSkusByIds(List<Long> skuIds);

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

    /**
     * 保存商品SKU
     *
     * @param spuId    SPU ID
     * @param skuList  SKU 列表
     */
    void saveSkus(Long spuId, List<SpuForm.Sku> skuList);

    /**
     * 根据SPU ID 查询商品SKU 列表
     *
     * @param spuId SPU ID
     * @return 商品 SKU 列表
     */
    List<SkuBO> listSkusBySpuId(Long spuId);
}
