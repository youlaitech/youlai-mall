package com.fly4j.shop.marketing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fly4j.shop.marketing.pojo.dto.SpikePeriodGoodsListDTO;
import com.fly4j.shop.marketing.pojo.entity.SpikePeriodGoodsEntity;

public interface ISpikePeriodGoodsService extends IService<SpikePeriodGoodsEntity> {


    boolean add(SpikePeriodGoodsListDTO spikePeriodGoodsListDTO);
}
