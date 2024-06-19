package com.youlai.mall.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.product.mapper.AttrMapper;
import com.youlai.mall.product.model.entity.Attr;
import com.youlai.mall.product.model.vo.AttrGroupVO;
import com.youlai.mall.product.service.AttrService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 属性服务实现类
 *
 * @author Ray
 * @since 2024/4/19
 */
@Service
@RequiredArgsConstructor
public class AttrServiceImpl extends ServiceImpl<AttrMapper, Attr> implements AttrService {

    /**
     * 根据分类ID获取基础属性列表
     *
     * @param categoryId 分类ID
     */
    @Override
    public List<AttrGroupVO> listAttrsByCategoryId(Long categoryId) {
        return this.baseMapper.listAttrsByCategoryId(categoryId);
    }

}
