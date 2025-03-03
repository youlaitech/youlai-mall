package com.youlai.mall.product.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.product.mapper.AttrMapper;
import com.youlai.mall.product.model.entity.Attr;
import com.youlai.mall.product.model.form.AttrForm;
import com.youlai.mall.product.model.query.AttrPageQuery;
import com.youlai.mall.product.model.vo.AttrVO;
import com.youlai.mall.product.service.AttrService;
import com.youlai.mall.product.service.SpuAttrService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    private final SpuAttrService spuAttrService;

    /**
     * 根据分类ID获取基础属性列表
     *
     * @param categoryId 分类ID
     */
    @Override
    public List<AttrVO> listAttrsByCategoryId(Long categoryId) {
        return this.baseMapper.listAttrsByCategoryId(categoryId);
    }

    @Override
    public IPage<AttrVO> getAttrPage(AttrPageQuery queryParams) {
        return null;
    }

    @Override
    public boolean saveAttr(AttrForm formData) {
        return false;
    }

    @Override
    public AttrForm getAttrForm(Long id) {
        return null;
    }

    @Override
    public boolean updateAttr(Long id, AttrForm formData) {
        return false;
    }

    @Override
    public void deleteAttrs(String ids) {

    }

}
