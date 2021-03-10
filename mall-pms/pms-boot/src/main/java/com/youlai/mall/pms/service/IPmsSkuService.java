package com.youlai.mall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.pojo.domain.PmsSku;
import com.youlai.mall.pms.pojo.dto.InventoryDTO;
import com.youlai.mall.pms.pojo.dto.InventoryNumDTO;

import java.util.List;

public interface IPmsSkuService extends IService<PmsSku> {

    /**
     * 锁定库存
     * @param list
     * @return 库存锁定结果
     */
    boolean lockInventory(List<InventoryNumDTO> list);

    /**
     * 解锁库存
     * @param  list
     * @return 解锁库存结果
     */
    boolean unlockInventory(List<InventoryNumDTO>  list);


    /**
     * 获取商品库存数量
     * @param id 库存ID
     * @return
     */
    Integer getInventoryById(Long id);


    /**
     * 获取库存列表
     * @param ids
     * @return
     */
    List<InventoryDTO> listBySkuIds(String ids);
}
