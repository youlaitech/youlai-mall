package com.youlai.mall.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.oms.mapper.OmsOrderSkuMapper;
import com.youlai.mall.oms.pojo.OmsOrderSku;
import com.youlai.mall.oms.service.IOmsOrderSkuService;
import org.springframework.stereotype.Service;

@Service
public class OmsOrderSkuServiceImpl extends ServiceImpl<OmsOrderSkuMapper, OmsOrderSku> implements IOmsOrderSkuService {
}
