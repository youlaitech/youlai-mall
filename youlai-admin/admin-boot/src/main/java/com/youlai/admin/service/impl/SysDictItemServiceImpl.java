package com.youlai.admin.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.admin.convert.DictItemConvert;
import com.youlai.admin.mapper.SysDictItemMapper;
import com.youlai.admin.pojo.entity.SysDictItem;
import com.youlai.admin.pojo.form.DictItemForm;
import com.youlai.admin.pojo.query.DictItemPageQuery;
import com.youlai.admin.pojo.vo.dict.DictItemPageVO;
import com.youlai.admin.service.SysDictItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
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

    private final DictItemConvert dictItemConvert;

    /**
     * 字典数据项分页列表
     *
     * @param queryParams
     * @return
     */
    @Override
    public Page<DictItemPageVO> listPageDictItems(DictItemPageQuery queryParams) {
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
                        .eq(StrUtil.isNotBlank(keywords), SysDictItem::getTypeCode, typeCode)
                        .select(SysDictItem::getId, SysDictItem::getName, SysDictItem::getTypeCode, SysDictItem::getStatus)
        );

        // 实体转换
        Page<DictItemPageVO> pageResult = dictItemConvert.entity2Page(dictItemPage);
        return pageResult;
    }

    /**
     * 字典数据项表单详情
     *
     * @param id 字典数据项ID
     * @return
     */
    @Override
    public DictItemForm getDictItemFormDetail(Long id) {
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
        DictItemForm dictItemForm = dictItemConvert.entity2Form(entity);
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
        SysDictItem entity = dictItemConvert.form2Entity(dictItemForm);
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
        SysDictItem entity = dictItemConvert.form2Entity(dictItemForm);
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
}
