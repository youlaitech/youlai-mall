package com.fly4j.shop.goods.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.shop.goods.mapper.GoodsCategoryMapper;
import com.fly4j.shop.goods.pojo.dto.GoodsCategoryDTO;
import com.fly4j.shop.goods.pojo.vo.TreeSelectVO;
import com.fly4j.shop.goods.service.IGoodsCategoryAttributeRelationService;
import com.fly4j.shop.goods.service.IGoodsCategoryService;
import com.fly4j.shop.goods.pojo.entity.GoodsCategory;
import com.fly4j.shop.goods.pojo.entity.GoodsCategoryAttributeRelation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GoodsCategoryServiceImpl extends ServiceImpl<GoodsCategoryMapper, GoodsCategory> implements IGoodsCategoryService {


    @Autowired
    private IGoodsCategoryAttributeRelationService iGoodsCategoryAttributeRelationService;

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
        TreeSelectVO node = new TreeSelectVO().setId(0L).setLabel("无上级分类").setChildren(null);
        list.add(node);
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


    @Override
    public boolean update(GoodsCategoryDTO goodsCategoryDTO) {
        // 修改商品分类
        GoodsCategory goodsCategory = new GoodsCategory();
        BeanUtil.copyProperties(goodsCategoryDTO, goodsCategory);
        this.updateById(goodsCategory);

        // 添加商品分类和属性的关联
        Set<Integer> dbAttributeIds = iGoodsCategoryAttributeRelationService.list(
                new LambdaQueryWrapper<GoodsCategoryAttributeRelation>()
                        .eq(GoodsCategoryAttributeRelation::getCategoryId, goodsCategory.getCategoryId())
        ).stream().map(relation -> relation.getAttributeId()).collect(Collectors.toSet());
        Set<Integer> formAttributeIds = goodsCategoryDTO.getGoodsAttributeIds();


        // 删除此次操作移除的属性
        Set<Integer> removeAttributeIds = new HashSet<>();
        removeAttributeIds.addAll(dbAttributeIds);
        removeAttributeIds.removeAll(formAttributeIds);
        if (CollUtil.isNotEmpty(removeAttributeIds)) {
            removeAttributeIds.forEach(removeAttributeId -> {
                iGoodsCategoryAttributeRelationService.remove(new LambdaQueryWrapper<GoodsCategoryAttributeRelation>()
                        .eq(GoodsCategoryAttributeRelation::getAttributeId, removeAttributeId)
                        .eq(GoodsCategoryAttributeRelation::getCategoryId, goodsCategory.getCategoryId())
                );
            });
        }

        // 添加此次操作新增的属性
        Set<Integer> addAttributeIds = new HashSet<>();
        addAttributeIds.addAll(formAttributeIds);
        addAttributeIds.removeAll(dbAttributeIds);
        if(CollUtil.isNotEmpty(addAttributeIds)){
            List<GoodsCategoryAttributeRelation> relations=new ArrayList<>();
            addAttributeIds.forEach(addAttributeId->{
                GoodsCategoryAttributeRelation relation = new GoodsCategoryAttributeRelation()
                        .setCategoryId(goodsCategory.getCategoryId())
                        .setAttributeId(addAttributeId);
                relations.add(relation);
            });
            iGoodsCategoryAttributeRelationService.saveBatch(relations);
        }
        return true;
    }

    @Override
    public boolean add(GoodsCategoryDTO goodsCategoryDTO) {
        // 添加商品分类
        GoodsCategory goodsCategory = new GoodsCategory();
        BeanUtil.copyProperties(goodsCategoryDTO, goodsCategory);
        this.save(goodsCategory);

        // 添加商品分类和属性的关联
        Set<Integer> goodsAttributeIds = goodsCategoryDTO.getGoodsAttributeIds();
        if (CollUtil.isNotEmpty(goodsAttributeIds)) {
            Integer categoryId = goodsCategory.getCategoryId();
            List<GoodsCategoryAttributeRelation> relations = new ArrayList<>();
            goodsAttributeIds.forEach(attributeId -> {
                GoodsCategoryAttributeRelation relation = new GoodsCategoryAttributeRelation()
                        .setCategoryId(categoryId)
                        .setAttributeId(attributeId);
                relations.add(relation);
            });

            iGoodsCategoryAttributeRelationService.saveBatch(relations);
        }
        return true;
    }

}
