package com.youlai.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.core.model.Option;
import com.youlai.system.converter.DictConverter;
import com.youlai.system.converter.DictItemConverter;
import com.youlai.system.mapper.DictMapper;
import com.youlai.system.model.entity.Dict;
import com.youlai.system.model.entity.DictItem;
import com.youlai.system.model.form.DictForm;
import com.youlai.system.model.query.DictPageQuery;
import com.youlai.system.model.vo.DictPageVO;
import com.youlai.system.service.DictItemService;
import com.youlai.system.service.SysDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据字典业务实现类
 *
 * @author haoxr
 * @since 2022/10/12
 */
@Service
@RequiredArgsConstructor
public class SysDictServiceImpl extends ServiceImpl<DictMapper, Dict> implements SysDictService {

    private final DictItemService dictItemService;
    private final DictConverter dictConverter;
    private final DictItemConverter dictItemConverter;

    /**
     * 字典分页列表
     *
     * @param queryParams 分页查询对象
     */
    @Override
    public Page<DictPageVO> getDictPage(DictPageQuery queryParams) {
        // 查询参数
        int pageNum = queryParams.getPageNum();
        int pageSize = queryParams.getPageSize();

        // 查询数据
        return this.baseMapper.getDictPage(new Page<>(pageNum, pageSize), queryParams);
    }

    /**
     * 新增字典
     *
     * @param dictForm 字典表单数据
     */
    @Override
    public boolean saveDict(DictForm dictForm) {
        // 保存字典
        Dict entity = dictConverter.convertToEntity(dictForm);

        // 校验 code 是否唯一
        long count = this.count(new LambdaQueryWrapper<Dict>()
                .eq(Dict::getCode, entity.getCode())
        );
        Assert.isTrue(count == 0, "字典编码已存在");

        boolean result = this.save(entity);
        // 保存字典项
        if (result) {
            List<DictForm.DictItem> dictFormDictItems = dictForm.getDictItems();
            List<DictItem> dictItems = dictItemConverter.convertToEntity(dictFormDictItems);
            dictItems.forEach(dictItem -> dictItem.setDictId(entity.getId()));
            dictItemService.saveBatch(dictItems);
        }
        return result;
    }


    /**
     * 获取字典表单详情
     *
     * @param id 字典ID
     */
    @Override
    public DictForm getDictForm(Long id) {
        // 获取字典
        Dict entity = this.getById(id);
        Assert.isTrue(entity != null, "字典不存在");
        DictForm dictForm = dictConverter.convertToForm(entity);

        // 获取字典项集合
        List<DictItem> dictItems = dictItemService.list(new LambdaQueryWrapper<DictItem>()
                .eq(DictItem::getDictId, id)
        );
        // 转换数据项
        List<DictForm.DictItem> dictItemList = dictItemConverter.convertToDictFormDictItem(dictItems);
        dictForm.setDictItems(dictItemList);
        return dictForm;
    }

    /**
     * 修改字典
     *
     * @param id       字典ID
     * @param dictForm 字典表单
     */
    @Override
    public boolean updateDict(Long id, DictForm dictForm) {
        // 更新字典
        Dict entity = dictConverter.convertToEntity(dictForm);
        
        // 校验 code 是否唯一
        long count = this.count(new LambdaQueryWrapper<Dict>()
                .eq(Dict::getCode, entity.getCode())
                .ne(Dict::getId, id)
        );
        Assert.isTrue(count == 0, "字典编码已存在");
        
        boolean result = this.updateById(entity);

        if (result) {
            // 更新字典项
            List<DictForm.DictItem> dictFormDictItems = dictForm.getDictItems();
            List<DictItem> dictItems = dictItemConverter.convertToEntity(dictFormDictItems);

            // 获取当前数据库中的字典项
            List<DictItem> currentDictItemEntities = dictItemService.list(new LambdaQueryWrapper<DictItem>()
                    .eq(DictItem::getDictId, id)
            );

            // 获取当前数据库中存在的字典项ID集合
            Set<Long> currentDictItemIds = currentDictItemEntities.stream()
                    .map(DictItem::getId)
                    .collect(Collectors.toSet());

            // 获取新提交的字典项ID集合
            Set<Long> newAttrIds = dictItems.stream()
                    .map(DictItem::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            // 需要删除的字典项ID集合（存在于数据库但不在新提交的字典项中）
            Set<Long> idsToDelete = new HashSet<>(currentDictItemIds);
            idsToDelete.removeAll(newAttrIds);

            // 删除不在新提交字典项中的旧字典项
            if (!idsToDelete.isEmpty()) {
                dictItemService.removeByIds(idsToDelete);
            }

            // 更新或新增字典项
            for (DictItem dictItem : dictItems) {
                if (dictItem.getId() != null && currentDictItemIds.contains(dictItem.getId())) {
                    // 更新现有字典项
                    dictItemService.updateById(dictItem);
                } else {
                    // 新增字典项
                    dictItem.setDictId(id);
                    dictItemService.save(dictItem);
                }
            }
        }
        return result;
    }

    /**
     * 删除字典
     *
     * @param ids 字典ID，多个以英文逗号(,)分割
     */
    @Override
    @Transactional
    public void deleteDictByIds(String ids) {

        Assert.isTrue(StrUtil.isNotBlank(ids), "请选择需要删除的字典");

        List<String> idList = Arrays.stream(ids.split(","))
                .toList();

        for (String id : idList) {
            boolean result = this.removeById(id);
            if (result) {
                // 删除字典下的字典项
                dictItemService.removeByDictId(Convert.toLong(id));
            }
        }
    }

    /**
     * 获取字典的数据项
     *
     * @param code 字典编码
     */
    @Override
    public List<Option> listDictItemsByCode(String code) {
        // 根据字典编码获取字典ID
        Dict dict = this.getOne(new LambdaQueryWrapper<Dict>()
                        .eq(Dict::getCode, code)
                .select(Dict::getId)
                .last("limit 1")
        );
        // 如果字典不存在，则返回空集合
        if (dict == null) {
            return CollectionUtil.newArrayList();
        }

        // 获取字典项
        List<DictItem> dictItems = dictItemService.list(
                new LambdaQueryWrapper<DictItem>()
                        .eq(DictItem::getDictId, dict.getId())
        );

        // 转换为 Option
        return dictItemConverter.convertToOption(dictItems);
    }


}




