package com.youlai.mall.ums.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.ums.pojo.domain.UmsMember;
import com.youlai.mall.ums.mapper.UmsUserMapper;
import com.youlai.mall.ums.service.IUmsUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UmsUserServiceImpl extends ServiceImpl<UmsUserMapper, UmsMember> implements IUmsUserService {


    @Override
    public IPage<UmsMember> list(Page<UmsMember> page, UmsMember spu) {
        List<UmsMember> list = this.baseMapper.list(page, spu);
        page.setRecords(list);
        return page;
    }
}
