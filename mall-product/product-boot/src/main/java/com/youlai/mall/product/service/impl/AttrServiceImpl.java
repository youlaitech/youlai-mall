package com.youlai.mall.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.product.converter.AttrConverter;
import com.youlai.mall.product.mapper.AttrMapper;
import com.youlai.mall.product.model.bo.AttrBO;
import com.youlai.mall.product.model.entity.Attr;
import com.youlai.mall.product.model.form.AttrForm;
import com.youlai.mall.product.model.vo.AttrVO;
import com.youlai.mall.product.service.AttrService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 商品属性服务实现类
 *
 * @author Ray.Hao
 * @since 2024/4/19
 */
@Service
@RequiredArgsConstructor
public class AttrServiceImpl extends ServiceImpl<AttrMapper, Attr> implements AttrService {

    private final AttrConverter attrConverter;

    /**
     * 根据分类ID获取基础属性列表
     *
     * @param categoryId 分类ID
     */
    @Override
    public List<AttrVO> getCategoryAttrs(Long categoryId) {
        List<AttrBO> attrs = this.baseMapper.getCategoryAttrs(categoryId);
        return attrConverter.toVO(attrs);
    }

    /**
     * 保存属性
     *
     * @param formData 属性表单
     * @return 是否成功
     */
    @Override
    public boolean saveAttr(AttrForm formData) {
        Attr entity = attrConverter.toEntity(formData);
        return this.save(entity);
    }

    /**
     * 获取属性表单
     *
     * @param id 属性ID
     * @return 属性表单
     */
    @Override
    public AttrForm getAttrForm(Long id) {
        Attr attr = this.getById(id);
        return attrConverter.toForm(attr);
    }

    /**
     * 更新属性
     *
     * @param id       属性ID
     * @param formData 属性表单
     * @return
     */
    @Override
    public boolean updateAttr(Long id, AttrForm formData) {
        Attr entity = attrConverter.toEntity(formData);
        return this.updateById(entity);
    }

    /**
     * 删除属性
     *
     * @param ids 属性ID，多个以英文逗号(,)分割
     */
    @Override
    public void deleteAttrs(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        this.removeByIds(idList);
    }

}
