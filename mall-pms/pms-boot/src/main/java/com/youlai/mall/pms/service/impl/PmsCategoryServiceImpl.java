package com.youlai.mall.pms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.constant.GlobalConstants;
import com.youlai.common.pojo.vo.CascadeVO;
import com.youlai.mall.pms.pojo.entity.PmsCategory;
import com.youlai.mall.pms.mapper.PmsCategoryMapper;
import com.youlai.mall.pms.service.IPmsCategoryService;
import com.youlai.mall.pms.pojo.vo.CategoryVO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PmsCategoryServiceImpl extends ServiceImpl<PmsCategoryMapper, PmsCategory> implements IPmsCategoryService {


    /**
     * 分类列表（树形）
     *
     * @param parentId
     * @return
     */

    @Cacheable(value = "pssss",key = "'categoryList:3333'")
    @Override
    public List<CategoryVO> listCategory(Long parentId) {
        List<PmsCategory> categoryList = this.list(new LambdaQueryWrapper<PmsCategory>()
                .eq(PmsCategory::getVisible, GlobalConstants.STATUS_YES).orderByDesc(PmsCategory::getSort));
        List<CategoryVO> list = recursionTree(parentId != null ? parentId : 0l, categoryList);
        return list;
    }


    private static List<CategoryVO> recursionTree(Long parentId, List<PmsCategory> categoryList) {
        List<CategoryVO> list = new ArrayList<>();
        Optional.ofNullable(categoryList)
                .ifPresent(categories ->
                        categories.stream().filter(category ->
                                category.getParentId().equals(parentId))
                                .forEach(category -> {
                                    CategoryVO categoryVO = new CategoryVO();
                                    BeanUtil.copyProperties(category, categoryVO);
                                    List<CategoryVO> children = recursionTree(category.getId(), categoryList);
                                    categoryVO.setChildren(children);
                                    list.add(categoryVO);
                                }));
        return list;
    }


    /**
     * 分类列表（级联）
     *
     * @return
     */
    @Override
    public List<CascadeVO> listCascadeCategory() {
        List<PmsCategory> categoryList = this.list(
                new LambdaQueryWrapper<PmsCategory>()
                        .eq(PmsCategory::getVisible, GlobalConstants.STATUS_YES)
                        .orderByAsc(PmsCategory::getSort)
        );
        List<CascadeVO> list = recursionCascade(0l, categoryList);
        return list;
    }

    private List<CascadeVO> recursionCascade(Long parentId, List<PmsCategory> categoryList) {
        List<CascadeVO> list = new ArrayList<>();
        Optional.ofNullable(categoryList)
                .ifPresent(categories ->
                        categories.stream().filter(category ->
                                category.getParentId().equals(parentId))
                                .forEach(category -> {
                                    CascadeVO categoryVO = new CascadeVO()
                                            .setLabel(category.getName())
                                            .setValue(category.getId().toString());
                                    BeanUtil.copyProperties(category, categoryVO);
                                    List<CascadeVO> children = recursionCascade(category.getId(), categoryList);
                                    categoryVO.setChildren(children);
                                    list.add(categoryVO);
                                })
                );
        return list;
    }


}
