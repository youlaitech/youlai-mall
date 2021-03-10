package com.youlai.mall.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.pms.mapper.PmsSpecificationMapper;
import com.youlai.mall.pms.pojo.domain.PmsSpecification;
import com.youlai.mall.pms.service.IPmsSpecificationService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author haoxr
 * @date 2020-11-06
 */
@Service
public class PmsSpecificationServiceImpl extends ServiceImpl<PmsSpecificationMapper, PmsSpecification> implements IPmsSpecificationService {

    @Override
    public List<PmsSpecification> listBySpuId(Long spuId) {
        List<PmsSpecification> list = this.baseMapper.listBySpuId(spuId);
        return list;
    }
}
