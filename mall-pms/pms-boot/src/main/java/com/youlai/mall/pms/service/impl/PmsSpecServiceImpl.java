package com.youlai.mall.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.pms.mapper.PmsSpecMapper;
import com.youlai.mall.pms.pojo.domain.PmsSpecification;
import com.youlai.mall.pms.service.IPmsSpecService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author haoxr
 * @date 2020-11-06
 */
@Service
public class PmsSpecServiceImpl extends ServiceImpl<PmsSpecMapper, PmsSpecification> implements IPmsSpecService {

    @Override
    public List<PmsSpecification> listBySpuId(Long spuId) {
        List<PmsSpecification> list = this.baseMapper.listBySpuId(spuId);
        return list;
    }
}
