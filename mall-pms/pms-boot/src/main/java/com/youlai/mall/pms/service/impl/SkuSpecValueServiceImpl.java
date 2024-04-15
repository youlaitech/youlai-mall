package com.youlai.mall.pms.service.impl;

import com.youlai.mall.pms.model.entity.SkuSpecValue;
import com.youlai.mall.pms.mapper.SkuSpecValueMapper;
import com.youlai.mall.pms.service.SkuSpecValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.youlai.common.util.DateUtils;
import com.youlai.mall.pms.model.form.SkuSpecValueForm;
import com.youlai.mall.pms.model.query.SkuSpecValuePageQuery;
import com.youlai.mall.pms.model.bo.SkuSpecValueBO;
import com.youlai.mall.pms.model.vo.SkuSpecValuePageVO;
import com.youlai.mall.pms.converter.SkuSpecValueConverter;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

/**
 * SKU规格值服务实现类
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
@Service
@RequiredArgsConstructor
public class SkuSpecValueServiceImpl extends ServiceImpl<SkuSpecValueMapper, SkuSpecValue> implements SkuSpecValueService {

    private final SkuSpecValueConverter skuSpecValueConverter;

    /**
    * 获取SKU规格值分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<SkuSpecValuePageVO>} SKU规格值分页列表
    */
    @Override
    public IPage<SkuSpecValuePageVO> listPagedSkuSpecValues(SkuSpecValuePageQuery queryParams) {
    
        // 参数构建
        int pageNum = queryParams.getPageNum();
        int pageSize = queryParams.getPageSize();
        Page<SkuSpecValueBO> page = new Page<>(pageNum, pageSize);

        // 格式化为数据库日期格式，避免日期比较使用格式化函数导致索引失效
        DateUtils.toDatabaseFormat(queryParams, "startTime", "endTime");
    
        // 查询数据
        Page<SkuSpecValueBO> boPage = this.baseMapper.listPagedSkuSpecValues(page, queryParams);
    
        // 实体转换
        return skuSpecValueConverter.bo2PageVo(boPage);
    }
    
    /**
     * 获取SKU规格值表单数据
     *
     * @param id SKU规格值ID
     * @return
     */
    @Override
    public SkuSpecValueForm getSkuSpecValueFormData(Long id) {
        SkuSpecValue entity = this.getById(id);
        return skuSpecValueConverter.entity2Form(entity);
    }
    
    /**
     * 新增SKU规格值
     *
     * @param formData SKU规格值表单对象
     * @return
     */
    @Override
    public boolean saveSkuSpecValue(SkuSpecValueForm formData) {
        // 实体转换 form->entity
        SkuSpecValue entity = skuSpecValueConverter.form2Entity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新SKU规格值
     *
     * @param id   SKU规格值ID
     * @param formData SKU规格值表单对象
     * @return
     */
    @Override
    public boolean updateSkuSpecValue(Long id,SkuSpecValueForm formData) {
        SkuSpecValue entity = skuSpecValueConverter.form2Entity(formData);
        return this.updateById(entity);
    }
    
    /**
     * 删除SKU规格值
     *
     * @param idsStr SKU规格值ID，多个以英文逗号(,)分割
     * @return true|false
     */
    @Override
    public boolean deleteSkuSpecValues(String idsStr) {
        Assert.isTrue(StrUtil.isNotBlank(idsStr), "删除的SKU规格值数据为空");
        // 逻辑删除
        List<Long> ids = Arrays.stream(idsStr.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        return this.removeByIds(ids);
    }
    

}
