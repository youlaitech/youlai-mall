package com.fly4j.yshop.pms.service;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fly4j.yshop.pms.pojo.dto.admin.PmsSkuDTO;
import com.fly4j.yshop.pms.pojo.entity.PmsSku;
import com.fly4j.yshop.pms.pojo.vo.SkuLockVO;

import java.util.List;
import java.util.Map;


public interface IPmsSkuService extends IService<PmsSku> {

    String checkAndLockStock(List<SkuLockVO> skuLockVOS);

    void unlockSku(String orderToken);

    Integer minusStock(Long sku_id, Integer sku_quantity);

    Page<PmsSkuDTO> list(Map<String, Object> params, Page<PmsSku> page);
}
