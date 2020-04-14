package com.fly4j.yshop.pms.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.yshop.pms.mapper.PmsGoodsMapper;
import com.fly4j.yshop.pms.pojo.entity.PmsSpu;
import com.fly4j.yshop.pms.service.IPmsGoodsService;
import org.springframework.stereotype.Service;


@Service
public class PmsGoodsServiceImpl extends ServiceImpl<PmsGoodsMapper, PmsSpu> implements IPmsGoodsService {

}
