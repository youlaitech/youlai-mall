package com.fly4j.shop.goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fly4j.shop.goods.pojo.dto.GoodsDTO;
import com.fly4j.shop.goods.pojo.dto.SpikeGoodsDTO;
import com.fly4j.shop.goods.pojo.entity.Goods;

import java.util.List;

public interface IGoodsService extends IService<Goods> {
    boolean add(GoodsDTO goodsDto);

    boolean updatePublishStatus(List<Long> ids, Integer publishStatus);

    boolean updateNewStatus(List<Long> ids, Integer newStatus);

    boolean updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    boolean updateDeleteStatus(List<Long> ids, Integer deleteStatus);

    boolean update(Long id,GoodsDTO goodsDto);

    Page<SpikeGoodsDTO> selectPage(Page<SpikeGoodsDTO> page, SpikeGoodsDTO spikeGoodsDTO);

<<<<<<< HEAD
    SpikeGoodsDTO selectById(Long id);
=======
    SeckillGoodsDTO selectById(Long id);

    GoodsDTO getUpdateInfo(Long id);
>>>>>>> 8a10aea310cbc9dec39bb10fc49bd1fa851e6743
}
