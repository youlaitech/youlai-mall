package com.youlai.admin.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.admin.converter.DictItemConverter;
import com.youlai.admin.mapper.SysDictItemMapper;
import com.youlai.admin.pojo.entity.SysDictItem;
import com.youlai.admin.pojo.form.DictItemForm;
import com.youlai.admin.pojo.query.DictItemPageQuery;
import com.youlai.admin.pojo.vo.dict.DictItemPageVO;
import com.youlai.admin.service.SysDictItemService;
import com.youlai.common.web.domain.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 字典数据项业务实现类
 *
 * @author haoxr
 * @date 2022/6/9
 */
@Service
@RequiredArgsConstructor
public class SysDictItemServiceImpl extends ServiceImpl<SysDictItemMapper, SysDictItem> implements SysDictItemService {

    private final DictItemConverter dictItemConverter;

    /**
     * 字典数据项分页列表
     *
     * @param queryParams
     * @return
     */
    @Override
    public Page<DictItemPageVO> listDictItemPages(DictItemPageQuery queryParams) {
        // 查询参数
        int pageNum = queryParams.getPageNum();
        int pageSize = queryParams.getPageSize();
        String keywords = queryParams.getKeywords();
        String typeCode = queryParams.getTypeCode();

        // 查询数据
        Page<SysDictItem> dictItemPage = this.page(
                new Page<>(pageNum, pageSize),
                new LambdaQueryWrapper<SysDictItem>()
                        .like(StrUtil.isNotBlank(keywords), SysDictItem::getName, keywords)
                        .eq(StrUtil.isNotBlank(typeCode), SysDictItem::getTypeCode, typeCode)
                        .select(SysDictItem::getId, SysDictItem::getName, SysDictItem::getValue, SysDictItem::getStatus)
        );

        // 实体转换
        Page<DictItemPageVO> pageResult = dictItemConverter.entity2Page(dictItemPage);
        return pageResult;
    }

    /**
     * 字典数据项表单详情
     *
     * @param id 字典数据项ID
     * @return
     */
    @Override
    public DictItemForm getDictItemFormData(Long id) {
        // 获取entity
        SysDictItem entity = this.getOne(new LambdaQueryWrapper<SysDictItem>()
                .eq(SysDictItem::getId, id)
                .select(
                        SysDictItem::getId,
                        SysDictItem::getTypeCode,
                        SysDictItem::getName,
                        SysDictItem::getValue,
                        SysDictItem::getStatus,
                        SysDictItem::getSort,
                        SysDictItem::getRemark
                ));
        Assert.isTrue(entity != null, "字典数据项不存在");

        // 实体转换
        DictItemForm dictItemForm = dictItemConverter.entity2Form(entity);
        return dictItemForm;
    }

    /**
     * 新增字典数据项
     *
     * @param dictItemForm 字典数据项表单
     * @return
     */
    @Override
    public boolean saveDictItem(DictItemForm dictItemForm) {
        // 实体对象转换 form->entity
        SysDictItem entity = dictItemConverter.form2Entity(dictItemForm);
        // 持久化
        boolean result = this.save(entity);
        return result;
    }

    /**
     * 修改字典数据项
     *
     * @param id           字典数据项ID
     * @param dictItemForm 字典数据项表单
     * @return
     */
    @Override
    public boolean updateDictItem(Long id, DictItemForm dictItemForm) {
        SysDictItem entity = dictItemConverter.form2Entity(dictItemForm);
        boolean result = this.updateById(entity);
        return result;
    }

    /**
     * 删除字典数据项
     *
     * @param idsStr 字典数据项ID，多个以英文逗号(,)分割
     * @return
     */
    @Override
    public boolean deleteDictItems(String idsStr) {
        Assert.isTrue(StrUtil.isNotBlank(idsStr), "删除数据为空");
        //
        List<Long> ids = Arrays.asList(idsStr.split(","))
                .stream()
                .map(id -> Long.parseLong(id))
                .collect(Collectors.toList());

        // 删除字典数据项
        boolean result = this.removeByIds(ids);
        return result;
    }


    /**
     * 根据字典类型编码获取字典数据项
     *
     * @param typeCode 字典类型编码
     * @return
     */
    @Override
    public List<Option> listDictItemsByTypeCode(String typeCode) {

        // 数据字典项
        List<SysDictItem> dictItems = this.list(new LambdaQueryWrapper<SysDictItem>()
                .eq(SysDictItem::getTypeCode, typeCode)
                .select(SysDictItem::getValue, SysDictItem::getName)
        );

        // 转换下拉数据
        List<Option> options = Optional.ofNullable(dictItems).orElse(new ArrayList<>()).stream()
                .map(dictItem -> new Option(dictItem.getValue(), dictItem.getName()))
                .collect(Collectors.toList());

        return options;
    }
}
