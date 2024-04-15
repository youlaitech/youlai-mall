package com.youlai.mall.pms.service.impl;

import com.youlai.mall.pms.model.entity.SpecValue;
import com.youlai.mall.pms.mapper.SpecValueMapper;
import com.youlai.mall.pms.service.SpecValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.youlai.common.util.DateUtils;
import com.youlai.mall.pms.model.form.SpecValueForm;
import com.youlai.mall.pms.model.query.SpecValuePageQuery;
import com.youlai.mall.pms.model.bo.SpecValueBO;
import com.youlai.mall.pms.model.vo.SpecValuePageVO;
import com.youlai.mall.pms.converter.SpecValueConverter;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

/**
 * 服务实现类
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
@Service
@RequiredArgsConstructor
public class SpecValueServiceImpl extends ServiceImpl<SpecValueMapper, SpecValue> implements SpecValueService {

    private final SpecValueConverter specValueConverter;

    /**
    * 获取分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<SpecValuePageVO>} 分页列表
    */
    @Override
    public IPage<SpecValuePageVO> listPagedSpecValues(SpecValuePageQuery queryParams) {
    
        // 参数构建
        int pageNum = queryParams.getPageNum();
        int pageSize = queryParams.getPageSize();
        Page<SpecValueBO> page = new Page<>(pageNum, pageSize);

        // 格式化为数据库日期格式，避免日期比较使用格式化函数导致索引失效
        DateUtils.toDatabaseFormat(queryParams, "startTime", "endTime");
    
        // 查询数据
        Page<SpecValueBO> boPage = this.baseMapper.listPagedSpecValues(page, queryParams);
    
        // 实体转换
        return specValueConverter.bo2PageVo(boPage);
    }
    
    /**
     * 获取表单数据
     *
     * @param id ID
     * @return
     */
    @Override
    public SpecValueForm getSpecValueFormData(Long id) {
        SpecValue entity = this.getById(id);
        return specValueConverter.entity2Form(entity);
    }
    
    /**
     * 新增
     *
     * @param formData 表单对象
     * @return
     */
    @Override
    public boolean saveSpecValue(SpecValueForm formData) {
        // 实体转换 form->entity
        SpecValue entity = specValueConverter.form2Entity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新
     *
     * @param id   ID
     * @param formData 表单对象
     * @return
     */
    @Override
    public boolean updateSpecValue(Long id,SpecValueForm formData) {
        SpecValue entity = specValueConverter.form2Entity(formData);
        return this.updateById(entity);
    }
    
    /**
     * 删除
     *
     * @param idsStr ID，多个以英文逗号(,)分割
     * @return true|false
     */
    @Override
    public boolean deleteSpecValues(String idsStr) {
        Assert.isTrue(StrUtil.isNotBlank(idsStr), "删除的数据为空");
        // 逻辑删除
        List<Long> ids = Arrays.stream(idsStr.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        return this.removeByIds(ids);
    }
    

}
