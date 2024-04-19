package com.youlai.mall.pms.service.impl;

import com.youlai.mall.pms.model.entity.Attribute;
import com.youlai.mall.pms.mapper.AttributeMapper;
import com.youlai.mall.pms.service.AttributeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.youlai.common.util.DateUtils;
import com.youlai.mall.pms.model.form.AttributeForm;
import com.youlai.mall.pms.model.query.AttributePageQuery;
import com.youlai.mall.pms.model.bo.AttributeBO;
import com.youlai.mall.pms.model.vo.AttributePageVO;
import com.youlai.mall.pms.converter.AttributeConverter;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

/**
 * 属性服务实现类
 *
 * @author Ray Hao
 * @since 2024-04-19
 */
@Service
@RequiredArgsConstructor
public class AttributeServiceImpl extends ServiceImpl<AttributeMapper, Attribute> implements AttributeService {

    private final AttributeConverter attributeConverter;

    /**
    * 获取属性分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AttributePageVO>} 属性分页列表
    */
    @Override
    public IPage<AttributePageVO> listPagedAttributes(AttributePageQuery queryParams) {
    
        // 参数构建
        int pageNum = queryParams.getPageNum();
        int pageSize = queryParams.getPageSize();
        Page<AttributeBO> page = new Page<>(pageNum, pageSize);

        // 格式化为数据库日期格式，避免日期比较使用格式化函数导致索引失效
        DateUtils.toDatabaseFormat(queryParams, "startTime", "endTime");
    
        // 查询数据
        Page<AttributeBO> boPage = this.baseMapper.listPagedAttributes(page, queryParams);
    
        // 实体转换
        return attributeConverter.bo2PageVo(boPage);
    }
    
    /**
     * 获取属性表单数据
     *
     * @param id 属性ID
     * @return
     */
    @Override
    public AttributeForm getAttributeFormData(Long id) {
        Attribute entity = this.getById(id);
        return attributeConverter.entity2Form(entity);
    }
    
    /**
     * 新增属性
     *
     * @param formData 属性表单对象
     * @return
     */
    @Override
    public boolean saveAttribute(AttributeForm formData) {
        // 实体转换 form->entity
        Attribute entity = attributeConverter.form2Entity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新属性
     *
     * @param id   属性ID
     * @param formData 属性表单对象
     * @return
     */
    @Override
    public boolean updateAttribute(Long id,AttributeForm formData) {
        Attribute entity = attributeConverter.form2Entity(formData);
        return this.updateById(entity);
    }
    
    /**
     * 删除属性
     *
     * @param ids 属性ID，多个以英文逗号(,)分割
     * @return true|false
     */
    @Override
    public boolean deleteAttributes(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的属性数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }
    

}
