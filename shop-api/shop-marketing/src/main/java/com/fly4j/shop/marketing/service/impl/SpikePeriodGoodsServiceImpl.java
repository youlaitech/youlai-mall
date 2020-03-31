package com.fly4j.shop.marketing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.shop.marketing.mapper.SpikePeriodGoodsMapper;
import com.fly4j.shop.marketing.pojo.dto.SpikePeriodGoodsDTO;
import com.fly4j.shop.marketing.pojo.dto.SpikePeriodGoodsListDTO;
import com.fly4j.shop.marketing.pojo.entity.SpikePeriodGoodsEntity;
import com.fly4j.shop.marketing.service.ISpikePeriodGoodsService;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SpikePeriodGoodsServiceImpl extends ServiceImpl<SpikePeriodGoodsMapper, SpikePeriodGoodsEntity> implements ISpikePeriodGoodsService {

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    /**
     * 设置秒杀时间段的商品
     *
     * @param spikePeriodGoodsListDTO
     * @return
     */
    @Override
    public boolean add(SpikePeriodGoodsListDTO spikePeriodGoodsListDTO) {
        List<SpikePeriodGoodsDTO> goodsDTOS = spikePeriodGoodsListDTO.getSpikePeriodGoodsList();
        if (goodsDTOS == null || goodsDTOS.size() <= 0) {
            throw new ApiException("设置的秒杀商品不能为空");
        }

        // 数据库秒杀商品
        Set<Long> dbGoodsIds = this.list(new LambdaQueryWrapper<SpikePeriodGoodsEntity>()
                .eq(SpikePeriodGoodsEntity::getSpikeId, spikePeriodGoodsListDTO.getSpikeId())
                .eq(SpikePeriodGoodsEntity::getSpikePeriodId, spikePeriodGoodsListDTO.getSpikePeriodId())
        ).stream().map(item -> item.getGoodsId()).collect(Collectors.toSet());

        Set<Long> goodsIds = goodsDTOS.stream().map(item -> item.getGoodsId()).collect(Collectors.toSet());

        // 此次删除的商品
        Set<Long> removeGoodsIds = new HashSet<>();
        removeGoodsIds.addAll(dbGoodsIds);
        removeGoodsIds.removeAll(goodsIds);
        if (removeGoodsIds.size() > 0) {
            this.remove(new LambdaQueryWrapper<SpikePeriodGoodsEntity>().in(SpikePeriodGoodsEntity::getGoodsId,removeGoodsIds));
        }

        // 新增或修改的商品
        List<SpikePeriodGoodsEntity> spikePeriodGoodsEntities = goodsDTOS.stream()
                .map(item -> {
                    SpikePeriodGoodsEntity spikePeriodGoodsEntity = dozerBeanMapper.map(item, SpikePeriodGoodsEntity.class);
                    spikePeriodGoodsEntity.setSpikeId(spikePeriodGoodsListDTO.getSpikeId())
                            .setSpikePeriodId(spikePeriodGoodsListDTO.getSpikePeriodId());
                    return spikePeriodGoodsEntity;
                }).collect(Collectors.toList());
        return this.saveOrUpdateBatch(spikePeriodGoodsEntities);
    }
}
