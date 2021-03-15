package com.youlai.mall.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.sms.mapper.SmsSeckillSkuRelationMapper;
import com.youlai.mall.sms.pojo.domain.SmsSeckillSkuRelation;
import com.youlai.mall.sms.service.ISmsSeckillSkuRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author huawei
 * @desc 秒杀活动场次商品关联业务实现类
 * @email huawei_code@163.com
 * @date 2021/3/5
 */
@Service
@Slf4j
public class SmsSeckillSkuRelationServiceImpl extends ServiceImpl<SmsSeckillSkuRelationMapper, SmsSeckillSkuRelation> implements ISmsSeckillSkuRelationService {
    @Override
    public List<SmsSeckillSkuRelation> selectBySessionId(Long sessionId) {
        log.info("根据秒杀活动场次ID查询关联商品列表，sessionId={}",sessionId);
        QueryWrapper<SmsSeckillSkuRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("session_id",sessionId);
        return this.list(queryWrapper);
    }
}
