package com.youlai.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.system.model.entity.SysDictItem;

/**
 * 字典项 接口
 *
 * @author Ray Hao
 * @since 2023/3/4
 */
public interface SysDictItemService extends IService<SysDictItem> {

    /**
     * 根据字典ID删除字典项
     *
     * @param dictId 字典ID
     */
    void removeByDictId(Long dictId);
}
