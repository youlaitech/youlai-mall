package com.youlai.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.admin.mapper.SysDictItemMapper;
import com.youlai.admin.pojo.entity.SysDictItem;
import com.youlai.admin.service.ISysDictItemService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 字典项业务实现类
 */
@Service
public class SysDictItemServiceImpl extends ServiceImpl<SysDictItemMapper, SysDictItem> implements ISysDictItemService {

    /**
     * 字典项分页列表
     *
     * @param page
     * @param dict
     * @return
     */
    @Override
    public IPage<SysDictItem> list(Page<SysDictItem> page, SysDictItem dict) {
        List<SysDictItem> list = this.baseMapper.list(page,dict);
        page.setRecords(list);
        return page;
    }
}
