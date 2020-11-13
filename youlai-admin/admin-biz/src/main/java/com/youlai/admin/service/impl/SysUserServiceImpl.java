package com.youlai.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.admin.pojo.SysUser;
import com.youlai.admin.mapper.SysUserMapper;
import com.youlai.admin.service.ISysUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    public IPage<SysUser> list(Page<SysUser> page,SysUser user ) {
        List<SysUser> list = this.baseMapper.list(page,user);
        page.setRecords(list);
        return page;
    }


}
