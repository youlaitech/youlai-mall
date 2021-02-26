package com.youlai.mall.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.pms.mapper.PmsCategorySpecMapper;
import com.youlai.mall.pms.pojo.domain.PmsCategorySpec;
import com.youlai.mall.pms.service.IPmsCategorySpecService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author haoxr
 * @date 2020-11-06
 */
@Service
public class PmsCategorySpecServiceImpl extends ServiceImpl<PmsCategorySpecMapper, PmsCategorySpec> implements IPmsCategorySpecService {

    @Override
    public List<PmsCategorySpec> listBySpuId(Long spuId) {
        List<PmsCategorySpec> list = this.baseMapper.listBySpuId(spuId);
        return list;
    }
}
