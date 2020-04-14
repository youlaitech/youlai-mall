package com.fly4j.yshop.pms.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.yshop.pms.mapper.PmsSpecificationMapper;
import com.fly4j.yshop.pms.pojo.entity.PmsSpec;
import com.fly4j.yshop.pms.service.IPmsSpecificationService;
import org.springframework.stereotype.Service;


@Service
public class PmsSpecificationServiceImpl extends ServiceImpl<PmsSpecificationMapper, PmsSpec> implements IPmsSpecificationService {

}
