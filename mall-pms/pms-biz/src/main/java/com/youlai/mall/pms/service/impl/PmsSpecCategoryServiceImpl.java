package com.youlai.mall.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.pms.mapper.PmsSpecCategoryMapper;
import com.youlai.mall.pms.pojo.PmsSpecCategory;
import com.youlai.mall.pms.service.IPmsSpecCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author haoxr
 * @date 2020-11-06
 */
@Service
public class PmsSpecCategoryServiceImpl extends ServiceImpl<PmsSpecCategoryMapper, PmsSpecCategory> implements IPmsSpecCategoryService {

    @Override
    public List<PmsSpecCategory> listBySpuId(Long spuId) {
        List<PmsSpecCategory> list = this.baseMapper.listBySpuId(spuId);
        return list;
    }
}
