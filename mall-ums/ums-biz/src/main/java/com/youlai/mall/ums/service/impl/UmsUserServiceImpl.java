package com.youlai.mall.ums.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.ums.pojo.UmsUser;
import com.youlai.mall.ums.mapper.UmsUserMapper;
import com.youlai.mall.ums.service.IUmsUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UmsUserServiceImpl extends ServiceImpl<UmsUserMapper, UmsUser> implements IUmsUserService {


    @Override
    public IPage<UmsUser> list(Page<UmsUser> page, UmsUser spu) {
        List<UmsUser> list = this.baseMapper.list(page, spu);
        page.setRecords(list);
        return page;
    }
}
