package com.youlai.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.web.exception.BusinessException;
import com.youlai.mall.product.mapper.AttrMapper;
import com.youlai.mall.product.model.entity.Attr;
import com.youlai.mall.product.model.vo.AttrGroupVO;
import com.youlai.mall.product.service.AttrService;
import com.youlai.mall.product.service.SpuAttrValueService;
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

    private final SpuAttrValueService spuAttrValueService;

    /**
     * 根据分类ID获取基础属性列表
     *
     * @param categoryId 分类ID
     */
    @Override
    public List<AttrGroupVO> listAttrsByCategoryId(Long categoryId) {
        return this.baseMapper.listAttrsByCategoryId(categoryId);
    }

    /**
     * 根据分组ID删除属性
     *
     * @param groupId 分组ID
     */
    @Override
    public void removeByGroupId(Long groupId) {
        List<Attr> attrList = this.list(new LambdaQueryWrapper<Attr>().eq(Attr::getAttrGroupId, groupId));

        attrList.forEach(attr -> {
            // 判断属性是否有商品引用
            boolean isAttrReferenced = spuAttrValueService.isAttrReferenced(attr.getId());
            if (isAttrReferenced) {
                throw new BusinessException( "属性【{}】已被商品引用，无法删除");
            }
            this.removeById(attr.getId());
        });
    }

}
