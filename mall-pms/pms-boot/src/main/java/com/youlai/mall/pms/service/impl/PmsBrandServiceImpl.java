package com.youlai.mall.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.pms.pojo.domain.PmsBrand;
import com.youlai.mall.pms.mapper.PmsBrandMapper;
import com.youlai.mall.pms.service.IPmsBrandService;
import org.springframework.stereotype.Service;

@Service
public class PmsBrandServiceImpl extends ServiceImpl<PmsBrandMapper, PmsBrand> implements IPmsBrandService {
}
