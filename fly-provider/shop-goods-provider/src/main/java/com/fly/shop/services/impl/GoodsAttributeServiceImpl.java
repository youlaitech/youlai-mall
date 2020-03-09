package com.fly.shop.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly.shop.dao.GoodsAttributeMapper;
import com.fly.shop.dao.GoodsAttributeTypeMapper;
import com.fly.shop.pojo.entity.GoodsAttribute;
import com.fly.shop.pojo.entity.GoodsAttributeType;
import com.fly.shop.services.IGoodsAttributeService;
import com.fly.shop.services.IGoodsAttributeTypeService;
import org.springframework.stereotype.Service;

@Service
public class GoodsAttributeServiceImpl extends ServiceImpl<GoodsAttributeMapper, GoodsAttribute> implements IGoodsAttributeService {

}
