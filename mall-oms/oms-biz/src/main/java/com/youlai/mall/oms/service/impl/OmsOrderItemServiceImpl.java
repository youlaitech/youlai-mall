package com.youlai.mall.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.oms.mapper.OmsOrderItemMapper;
import com.youlai.mall.oms.pojo.OmsOrderItem;
import com.youlai.mall.oms.service.IOmsOrderItemService;
import org.springframework.stereotype.Service;

@Service
public class OmsOrderItemServiceImpl extends ServiceImpl<OmsOrderItemMapper, OmsOrderItem> implements IOmsOrderItemService {
}
