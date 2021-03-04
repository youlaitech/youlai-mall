package com.youlai.mall.sms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.sms.mapper.SmsSeckillSkuRelationMapper;
import com.youlai.mall.sms.pojo.domain.SmsSeckillSkuRelation;
import com.youlai.mall.sms.service.SmsSeckillSkuRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author huawei
 * @desc 秒杀活动场次商品关联业务实现类
 * @email huawei_code@163.com
 * @date 2021/3/5
 */
@Service
@Slf4j
public class SmsSeckillSkuRelationServiceImpl extends ServiceImpl<SmsSeckillSkuRelationMapper, SmsSeckillSkuRelation> implements SmsSeckillSkuRelationService {
}
