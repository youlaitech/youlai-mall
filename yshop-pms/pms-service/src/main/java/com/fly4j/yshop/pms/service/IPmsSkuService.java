package com.fly4j.yshop.pms.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.fly4j.yshop.pms.pojo.entity.PmsSku;
import com.fly4j.yshop.pms.pojo.vo.SkuLockVO;

import java.util.List;


public interface IPmsSkuService extends IService<PmsSku> {

    String checkAndLockStock(List<SkuLockVO> skuLockVOS);

}
