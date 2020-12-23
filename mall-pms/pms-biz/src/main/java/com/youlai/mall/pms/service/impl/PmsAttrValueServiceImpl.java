package com.youlai.mall.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.pms.pojo.PmsSpuAttr;
import com.youlai.mall.pms.mapper.PmsSpuAttrMapper;
import com.youlai.mall.pms.service.IPmsAttrValueService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author haoxr
 * @date 2020-12-12
 */
@Service
public class PmsAttrValueServiceImpl extends ServiceImpl<PmsSpuAttrMapper, PmsSpuAttr> implements IPmsAttrValueService {

    @Override
    public List<PmsSpuAttr> listBySpuId(Long spuId) {
        return this.baseMapper.listBySpuId(spuId);
    }
}
