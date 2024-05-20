package com.youlai.mall.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.constant.GlobalConstants;
import com.youlai.common.web.model.Option;
import com.youlai.mall.product.converter.CategoryConverter;
import com.youlai.mall.product.mapper.CategoryMapper;
import com.youlai.mall.product.model.entity.Category;
import com.youlai.mall.product.model.form.CategoryForm;
import com.youlai.mall.product.model.vo.CategoryVO;
import com.youlai.mall.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 商品分类服务实现类
 *
 * @author Ray Hao
 * @since 2024/04/20
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    private final CategoryConverter categoryConverter;

    /**
     * 分类列表（树形）
     *
     * @param parentId 父分类ID
     * @return 分类列表
     */
    @Override
    public List<CategoryVO> listCategories(Long parentId) {
        List<Category> categoryList = this.list(
                new LambdaQueryWrapper<Category>()
                        .eq(Category::getIsVisible, GlobalConstants.STATUS_YES)
                        .orderByAsc(Category::getSort)
        );
        return buildTree(parentId != null ? parentId : 0L, categoryList,
                category -> {
                    CategoryVO categoryVO = new CategoryVO();
                    BeanUtil.copyProperties(category, categoryVO);

                    String treePath = category.getTreePath();
                    if (StrUtil.isNotBlank(treePath)){
                        // 根据 treePath 转为 level  0,1 是二级， 0,1,2 是三级
                        Integer level = treePath.split(",").length;
                        categoryVO.setLevel(level);
                    }
                    return categoryVO;
                },
                CategoryVO::setChildren
        );
    }


    /**
     * 分类列表（级联）
     *
     * @return 分类列表选项
     */
    @Override
    public List<Option> listCategoryOptions() {
        List<Category> categoryList = this.list(
                new LambdaQueryWrapper<Category>()
                        .eq(Category::getIsVisible, GlobalConstants.STATUS_YES)
                        .orderByAsc(Category::getSort)
        );
        return buildTree(0L, categoryList,
                category -> new Option<>(category.getId(), category.getName()),
                (option, children) -> ((Option<?>) option).setChildren(children)
        );
    }

    /**
     * 通用的递归树构建方法
     *
     * @param parentId     父分类ID
     * @param categoryList 分类列表
     * @param converter    实体转换器
     * @param childSetter  子节点设置器，用于将子节点列表设置到父节点上
     * @return 构建好的树形结构列表
     */
    private <T> List<T> buildTree(Long parentId, List<Category> categoryList, Function<Category, T> converter, BiConsumer<T, List<T>> childSetter) {
        return categoryList.stream()
                .filter(category -> category.getParentId().equals(parentId))
                .map(category -> {
                    T node = converter.apply(category);
                    List<T> children = buildTree(category.getId(), categoryList, converter, childSetter);
                    childSetter.accept(node, children);
                    return node;
                })
                .collect(Collectors.toList());
    }

    /**
     * 新增/修改分类
     *
     * @param formData 分类表单数据
     * @return 分类ID
     */
    @Override
    public Long saveCategory(CategoryForm formData) {
        // 转成实体对象
        Category category = categoryConverter.convertToEntity(formData);
        // 构建层级路径
        String treePath = buildTreePath(formData.getParentId());
        category.setTreePath(treePath);
        // 保存分类
        this.saveOrUpdate(category);
        return category.getId();
    }

    /**
     * 删除分类
     *
     * @param id 分类ID
     */
    @Override
    public void deleteCategory(Long id) {
        // 删除分类及子分类
        this.remove(new LambdaQueryWrapper<Category>()
                .eq(Category::getId, id)
                .or()
                .apply("CONCAT (',',tree_path,',') LIKE CONCAT('%,',{0},',%')", id)
        );
    }

    /**
     * 获取分类表单数据
     *
     * @param id 分类ID
     * @return 分类表单数据
     */
    @Override
    public CategoryForm getCategoryForm(Long id) {
        Category entity = this.getById(id);
        return categoryConverter.convertToForm(entity);
    }


    /**
     * 构建部门层级路径
     *
     * @param parentId 父ID
     * @return 父节点路径以英文逗号(, )分割，eg: 1,2,3
     */
    private String buildTreePath(Long parentId) {
        String treePath = null;
        if (GlobalConstants.ROOT_NODE_ID.equals(parentId)) {
            treePath = String.valueOf(parentId);
        } else {
            Category parent = this.getById(parentId);
            if (parent != null) {
                treePath = parent.getTreePath() + "," + parent.getId();
            }
        }
        return treePath;
    }
}
