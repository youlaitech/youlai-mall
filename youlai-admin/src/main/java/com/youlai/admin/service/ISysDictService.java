package com.youlai.admin.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.admin.domain.entity.SysDict;

public interface ISysDictService extends IService<SysDict> {

    IPage<SysDict> list(Page<SysDict> page, SysDict dict);
}
