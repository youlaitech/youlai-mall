package com.youlai.mall.pms.service.impl;

import com.youlai.mall.pms.model.entity.SpuImage;
import com.youlai.mall.pms.mapper.SpuImageMapper;
import com.youlai.mall.pms.service.SpuImageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.youlai.common.util.DateUtils;
import com.youlai.mall.pms.model.form.SpuImageForm;
import com.youlai.mall.pms.model.query.SpuImagePageQuery;
import com.youlai.mall.pms.model.bo.SpuImageBO;
import com.youlai.mall.pms.model.vo.SpuImagePageVO;
import com.youlai.mall.pms.converter.SpuImageConverter;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

/**
 * 商品图片服务实现类
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
@Service
@RequiredArgsConstructor
public class SpuImageServiceImpl extends ServiceImpl<SpuImageMapper, SpuImage> implements SpuImageService {

    private final SpuImageConverter spuImageConverter;

    /**
    * 获取商品图片分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<SpuImagePageVO>} 商品图片分页列表
    */
    @Override
    public IPage<SpuImagePageVO> listPagedSpuImages(SpuImagePageQuery queryParams) {
    
        // 参数构建
        int pageNum = queryParams.getPageNum();
        int pageSize = queryParams.getPageSize();
        Page<SpuImageBO> page = new Page<>(pageNum, pageSize);

        // 格式化为数据库日期格式，避免日期比较使用格式化函数导致索引失效
        DateUtils.toDatabaseFormat(queryParams, "startTime", "endTime");
    
        // 查询数据
        Page<SpuImageBO> boPage = this.baseMapper.listPagedSpuImages(page, queryParams);
    
        // 实体转换
        return spuImageConverter.bo2PageVo(boPage);
    }
    
    /**
     * 获取商品图片表单数据
     *
     * @param id 商品图片ID
     * @return
     */
    @Override
    public SpuImageForm getSpuImageFormData(Long id) {
        SpuImage entity = this.getById(id);
        return spuImageConverter.entity2Form(entity);
    }
    
    /**
     * 新增商品图片
     *
     * @param formData 商品图片表单对象
     * @return
     */
    @Override
    public boolean saveSpuImage(SpuImageForm formData) {
        // 实体转换 form->entity
        SpuImage entity = spuImageConverter.form2Entity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新商品图片
     *
     * @param id   商品图片ID
     * @param formData 商品图片表单对象
     * @return
     */
    @Override
    public boolean updateSpuImage(Long id,SpuImageForm formData) {
        SpuImage entity = spuImageConverter.form2Entity(formData);
        return this.updateById(entity);
    }
    
    /**
     * 删除商品图片
     *
     * @param idsStr 商品图片ID，多个以英文逗号(,)分割
     * @return true|false
     */
    @Override
    public boolean deleteSpuImages(String idsStr) {
        Assert.isTrue(StrUtil.isNotBlank(idsStr), "删除的商品图片数据为空");
        // 逻辑删除
        List<Long> ids = Arrays.stream(idsStr.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        return this.removeByIds(ids);
    }
    

}
