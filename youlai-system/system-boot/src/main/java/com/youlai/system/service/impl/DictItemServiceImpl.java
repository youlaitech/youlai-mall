package com.youlai.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.system.mapper.DictDataMapper;
import com.youlai.system.model.entity.DictData;
import com.youlai.system.service.DictItemService;
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
public class DictItemServiceImpl extends ServiceImpl<DictDataMapper, DictData> implements DictItemService {

    /**
     * 根据字典ID删除字典项
     *
     * @param dictId 字典ID
     */
    @Override
    public void removeByDictId(Long dictId) {

    }
}




