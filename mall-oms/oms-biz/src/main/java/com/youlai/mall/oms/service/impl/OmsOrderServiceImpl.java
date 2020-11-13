package com.youlai.mall.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.oms.pojo.OmsOrder;
import com.youlai.mall.oms.mapper.OmsOrderMapper;
import com.youlai.mall.oms.service.IOmsOrderService;
import org.springframework.stereotype.Service;

@Service
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderMapper, OmsOrder> implements IOmsOrderService {
}
