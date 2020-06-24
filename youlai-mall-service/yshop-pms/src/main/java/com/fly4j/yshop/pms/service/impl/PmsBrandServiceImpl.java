package com.fly4j.yshop.pms.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.yshop.pms.mapper.PmsBrandMapper;
import com.fly4j.yshop.pms.pojo.entity.PmsBrand;
import com.fly4j.yshop.pms.service.IPmsBrandService;
import org.springframework.stereotype.Service;


@Service
public class PmsBrandServiceImpl extends ServiceImpl<PmsBrandMapper, PmsBrand> implements IPmsBrandService {

}
