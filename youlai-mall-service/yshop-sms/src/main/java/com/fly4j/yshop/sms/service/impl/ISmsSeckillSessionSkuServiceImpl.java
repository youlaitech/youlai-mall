package com.fly4j.yshop.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.yshop.sms.mapper.SmsSeckillSessionSkuMapper;
import com.fly4j.yshop.sms.pojo.dto.admin.SmsSeckillSessionSkuDTO;
import com.fly4j.yshop.sms.pojo.entity.SmsSeckillSessionSku;
import com.fly4j.yshop.sms.service.ISmsSeckillSessionSkuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author hxrui
 * @date 2020-04-13 15:02
 **/
@Service
public class ISmsSeckillSessionSkuServiceImpl extends ServiceImpl<SmsSeckillSessionSkuMapper, SmsSeckillSessionSku> implements ISmsSeckillSessionSkuService {

    /**
     * 保存秒杀商品列表
     *
     * @return
     */
    @Override
    public boolean save(SmsSeckillSessionSkuDTO smsSeckillSessionSkuDTO) {
        Long promotion_id = smsSeckillSessionSkuDTO.getPromotion_id();
        Long promotion_session_id = smsSeckillSessionSkuDTO.getPromotion_session_id();

        List<SmsSeckillSessionSku> dbSkuList = this.baseMapper.selectList(new LambdaQueryWrapper<SmsSeckillSessionSku>()
                .eq(SmsSeckillSessionSku::getPromotion_id, promotion_id)
                .eq(SmsSeckillSessionSku::getPromotion_session_Id, promotion_session_id)
        );
        Set<SmsSeckillSessionSku> formSkuList = smsSeckillSessionSkuDTO.getSku_list().stream()
                .map(item->{
                    item.setPromotion_id(promotion_id).setPromotion_session_Id(promotion_session_id);
                    return item;
                }).collect(Collectors.toSet());

        Set<Long> formIds = formSkuList.stream().filter(item -> item.getId() != null)
                .map(item -> item.getId()).collect(Collectors.toSet());

        // 删除被移除的秒杀商品
        if (dbSkuList != null && dbSkuList.size() > 0) {
            Set<Long> deleteIds = dbSkuList.stream().
                    filter(db -> !formIds.contains(db.getId()))
                    .map(item -> item.getId()).collect(Collectors.toSet());
            this.baseMapper.deleteBatchIds(deleteIds);
        }
        return this.saveOrUpdateBatch(formSkuList);
    }
}