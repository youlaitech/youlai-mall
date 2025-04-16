package com.youlai.mall.product.service.impl;

import com.youlai.mall.product.model.entity.CategorySpecEntity;
import com.youlai.mall.product.mapper.CategorySpecMapper;
import com.youlai.mall.product.model.vo.SpecVO;
import com.youlai.mall.product.service.CategorySpecService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.youlai.mall.product.model.form.SpecForm;
import com.youlai.mall.product.model.bo.SpecBO;
import com.youlai.mall.product.converter.SpecConverter;

import java.util.Arrays;
import java.util.List;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

/**
 * 规格服务实现类
 *
 * @author Ray.Hao
 * @since 2024-06-13
 */
@Service
@RequiredArgsConstructor
public class CategorySpecServiceImpl extends ServiceImpl<CategorySpecMapper, CategorySpecEntity> implements CategorySpecService {

    private final SpecConverter specConverter;

    /**
     * 获取规格表单数据
     *
     * @param id ID
     */
    @Override
    public SpecForm getSpecFormData(Long id) {
        CategorySpecEntity entity = this.getById(id);
        return specConverter.toForm(entity);
    }

    /**
     * 新增规格
     *
     * @param formData 表单对象
     * @return
     */
    @Override
    public boolean saveSpec(SpecForm formData) {
        // 实体转换 form->entity
        CategorySpecEntity entity = specConverter.toEntity(formData);
        return this.save(entity);
    }

    /**
     * 更新规格
     *
     * @param id   规格ID
     * @param formData 规格表单对象
     * @return
     */
    @Override
    public boolean updateSpec(Long id, SpecForm formData) {
        CategorySpecEntity entity = specConverter.toEntity(formData);
        return this.updateById(entity);
    }

    /**
     * 删除规格
     *
     * @param ids ID，多个以英文逗号(,)分割
     */
    @Override
    public boolean deleteSpecs(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

    /**
     * 获取分类关联规格
     *
     * @param categoryId 分类ID
     * @return 规格列表
     */
    @Override
    public List<SpecVO> getCategorySpecs(Long categoryId) {
        List<SpecBO> list = this.baseMapper.getCategorySpecs(categoryId);
        return specConverter.toVO(list);
    }

}
