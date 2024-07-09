package com.youlai.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.system.mapper.SysDictItemMapper;
import com.youlai.system.model.entity.SysDictItem;
import com.youlai.system.service.SysDictItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 数据字典 服务实现类
 *
 * @author haoxr
 * @since 2022/10/12
 */
@Service
@RequiredArgsConstructor
public class SysDictItemServiceImpl extends ServiceImpl<SysDictItemMapper, SysDictItem> implements SysDictItemService {

    /**
     * 根据字典ID删除字典项
     *
     * @param dictId 字典ID
     */
    @Override
    public void removeByDictId(Long dictId) {

    }
}




