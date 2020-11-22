package com.youlai.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.admin.pojo.SysDict;
import com.youlai.admin.mapper.SysDictMapper;
import com.youlai.admin.service.ISysDictService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    @Override
    public IPage<SysDict> list(Page<SysDict> page, SysDict dict) {
        List<SysDict> list = this.baseMapper.list(page,dict);
        page.setRecords(list);
        return page;
    }

    /**
     * 更新字典表Code值
     *
     * @param oldCode 旧 code
     * @param newCode 新 code
     * @return 更新关联字典数量
     */
    @Override
    public Integer updateTypeCode(String oldCode, String newCode) {
        return this.baseMapper.updateTypeCode(oldCode,newCode);
    }

    @Override
    public Integer countByDictTypeIds(List<Long> ids) {

        return this.baseMapper.countByDictTypeIds(ids);
    }
}
