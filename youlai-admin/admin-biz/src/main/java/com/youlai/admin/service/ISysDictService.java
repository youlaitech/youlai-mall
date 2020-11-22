package com.youlai.admin.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.admin.pojo.SysDict;

import java.util.List;

public interface ISysDictService extends IService<SysDict> {

    IPage<SysDict> list(Page<SysDict> page, SysDict dict);

    /**
     * 更新字典表Code值
     *
     * @param oldCode 旧 code
     * @param newCode 新 code
     * @return
     */
    Integer updateTypeCode(String oldCode, String newCode);

    /**
     * 根据字典类型id查询管理字典数量
     * @param ids 字典类型id集合
     * @return
     */
    Integer countByDictTypeIds(List<Long> ids);
}
