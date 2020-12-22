package com.youlai.mall.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.pms.pojo.PmsAttrValue;
import com.youlai.mall.pms.mapper.PmsAttrValueMapper;
import com.youlai.mall.pms.service.IPmsAttrValueService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author haoxr
 * @date 2020-12-12
 */
@Service
public class PmsAttrValueServiceImpl extends ServiceImpl<PmsAttrValueMapper, PmsAttrValue> implements IPmsAttrValueService {

    @Override
    public List<PmsAttrValue> listBySpuId(Long spuId) {
        return this.baseMapper.listBySpuId(spuId);
    }
}
