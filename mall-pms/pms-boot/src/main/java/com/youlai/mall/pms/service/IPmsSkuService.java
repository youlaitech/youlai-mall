package com.youlai.mall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.pojo.domain.PmsSku;
import com.youlai.mall.pms.pojo.dto.SkuDTO;
import com.youlai.mall.pms.pojo.dto.SkuLockDTO;

import java.util.List;

public interface IPmsSkuService extends IService<PmsSku> {

    /**
     * 锁定库存
     * @param list
     * @return 库存锁定结果
     */
    boolean lockStock(List<SkuLockDTO> list);

    /**
     * 解锁库存
     * @param  list
     * @return 解锁库存结果
     */
    boolean unlockStock(List<SkuLockDTO>  list);


    /**
     * 获取商品库存数量
     * @param id 库存ID
     * @return
     */
    Integer getStockById(Long id);


    /**
     * 获取库存列表
     * @param ids
     * @return
     */
    List<SkuDTO> listBySkuIds(List<Long> ids);

    boolean deductStock(List<SkuLockDTO> list);
}
