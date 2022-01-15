package com.youlai.admin.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.admin.mapper.SysDictMapper;
import com.youlai.admin.pojo.entity.SysDict;
import com.youlai.admin.pojo.entity.SysDictItem;
import com.youlai.admin.service.ISysDictItemService;
import com.youlai.admin.service.ISysDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典业务实现类
 */
@Service
@RequiredArgsConstructor
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    private final ISysDictItemService iSysDictItemService;

    /**
     * 修改字典
     *
     * @param newDict
     * @return
     */
    @Override
    @Transactional
    public boolean updateDictById(Long id, SysDict newDict) {
        SysDict oldDict = this.getById(id);
        Assert.isTrue(oldDict != null, "字典不存在");
        if (!StrUtil.equals(newDict.getCode(), oldDict.getCode())) {
            // 字典编码变化，同步修改字典项的字典编码
            iSysDictItemService.update(new LambdaUpdateWrapper<SysDictItem>()
                    .eq(SysDictItem::getDictCode, oldDict.getCode())
                    .set(SysDictItem::getDictCode, newDict.getCode())
            );
        }
        boolean result = this.updateById(newDict);
        return result;
    }


    /**
     * 批量删除字典
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public boolean deleteDictByIds(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        Assert.isTrue(CollectionUtil.isNotEmpty(idList), "删除对象为空");

        List<SysDict> dictList = this.list(new LambdaQueryWrapper<SysDict>()
                .in(SysDict::getId, idList));
        if (CollectionUtil.isNotEmpty(dictList)) {
            List<String> dictCodes = dictList.stream().map(item -> item.getCode()).collect(Collectors.toList());
            // 批量删除字典项
            iSysDictItemService.remove(new LambdaQueryWrapper<SysDictItem>()
                    .in(SysDictItem::getDictCode, dictCodes)
            );
        }
        // 批量删除字典
        boolean result = this.removeByIds(idList);
        return result;
    }
}
