package com.fly.shop.services.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly.shop.dao.GoodsCategoryMapper;
import com.fly.shop.pojo.dto.GoodsCategoryDTO;
import com.fly.shop.pojo.entity.GoodsCategory;
import com.fly.shop.pojo.vo.TreeSelectVO;
import com.fly.shop.services.IGoodsCategoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsCategoryServiceImpl extends ServiceImpl<GoodsCategoryMapper, GoodsCategory> implements IGoodsCategoryService {

    @Override
    public Page<GoodsCategory> selectPage(Page<GoodsCategory> page, GoodsCategory goodsCategory) {
        List<GoodsCategory> list = this.baseMapper.page(page, goodsCategory);
        page.setRecords(list);
        return page;
    }

    @Override
    public List<GoodsCategoryDTO> selectList(GoodsCategory goodsCategory) {
        // 按照"分类名称"模糊查询
        List<GoodsCategory> list = this.baseMapper.selectList(
                new LambdaQueryWrapper<GoodsCategory>().like(StringUtils.isNotBlank(goodsCategory.getCategoryName()),
                        GoodsCategory::getCategoryName, goodsCategory.getCategoryName()));
        List<GoodsCategoryDTO> categoryList = CollUtil.newLinkedList();
        // entity需和数据库表字段一一对应，这里使用DTO扩展了children属性
        list.forEach(category -> {
            GoodsCategoryDTO categoryDTO = new GoodsCategoryDTO();
            BeanUtil.copyProperties(category, categoryDTO);
            categoryList.add(categoryDTO);
        });

        List<GoodsCategoryDTO> resultList = CollUtil.newLinkedList();
        if (categoryList != null && categoryList.size() > 0) {
            categoryList.forEach(category -> {
                if (category.getParentId().equals(0)) {
                    buildTree(category, categoryList, resultList);
                }
            });
        }

        if (resultList.isEmpty()) {
            return categoryList;
        }

        return resultList;
    }


    private void buildTree(GoodsCategoryDTO parentCategory, List<GoodsCategoryDTO> categoryList, List<GoodsCategoryDTO> resultList) {
        List<GoodsCategoryDTO> children = CollUtil.newLinkedList();
        categoryList.forEach(category -> {
            if (category.getParentId().equals(parentCategory.getCategoryId())) {
                children.add(category);
                buildTree(category, categoryList, resultList);
            }
        });
        parentCategory.setChildren(children);

        // 只添加是顶级分类延伸的分支
        if (parentCategory.getParentId().equals(0)) {
            resultList.add(parentCategory);
        }
    }


    @Override
    public List<TreeSelectVO> treeSelect(GoodsCategory goodsCategory) {
        List<GoodsCategory> categories = this.baseMapper.selectList(new LambdaQueryWrapper<GoodsCategory>()
                .like(StringUtils.isNotBlank(goodsCategory.getCategoryName()), GoodsCategory::getCategoryName, goodsCategory.getCategoryName()));
        List<TreeSelectVO> list = CollUtil.newLinkedList();
        categories.forEach(category -> buildTreeSelect(category, categories, list));
        return list;
    }

    private void buildTreeSelect(GoodsCategory parentCategory, List<GoodsCategory> categories, List<TreeSelectVO> list) {
        TreeSelectVO treeSelectVO = new TreeSelectVO();
        treeSelectVO.setId(Long.valueOf(parentCategory.getCategoryId()));
        treeSelectVO.setLabel(parentCategory.getCategoryName());
        List<TreeSelectVO> children = CollUtil.newLinkedList();
        treeSelectVO.setChildren(children);
        categories.forEach(category -> {
            if (category.getParentId().equals(parentCategory.getCategoryId())) {
                TreeSelectVO child = new TreeSelectVO();
                child.setId(Long.valueOf(category.getCategoryId()));
                child.setLabel(category.getCategoryName());
                children.add(child);
                buildTreeSelect(category, categories, list);
            }
        });
        if (parentCategory.getParentId().equals(0)) {
            list.add(treeSelectVO);
        }
    }
}
