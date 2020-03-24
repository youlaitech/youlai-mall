package com.fly.shop.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fly.shop.pojo.dto.GoodsDTO;
import com.fly.shop.pojo.entity.Goods;

import java.util.List;

public interface IGoodsService extends IService<Goods> {
    boolean add(GoodsDTO goodsDto);

    boolean updatePublishStatus(List<Long> ids, Integer publishStatus);

    boolean updateNewStatus(List<Long> ids, Integer newStatus);

    boolean updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    boolean updateDeleteStatus(List<Long> ids, Integer deleteStatus);

    boolean update(Long id,GoodsDTO goodsDto);
}
