package com.youlai.mall.pms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.pms.entity.PmsSpu;
import com.youlai.mall.pms.mapper.PmsSpuMapper;
import com.youlai.mall.pms.service.IPmsSpuService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PmsSpuServiceImpl extends ServiceImpl<PmsSpuMapper, PmsSpu> implements IPmsSpuService {
    @Override
    public IPage<PmsSpu> list(Page<PmsSpu> page, PmsSpu spu) {
        List<PmsSpu> list = this.baseMapper.list(page, spu);
        page.setRecords(list);
        return page;
    }
}
