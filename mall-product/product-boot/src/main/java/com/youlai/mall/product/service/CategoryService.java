package com.youlai.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.common.web.model.Option;
import com.youlai.mall.product.enums.AttributeTypeEnum;
import com.youlai.mall.product.model.entity.Category;
import com.youlai.mall.product.model.form.CategoryForm;
import com.youlai.mall.product.model.vo.CategoryAppVO;
import com.youlai.mall.product.model.vo.CategoryVO;

import java.util.List;


/**
 * 商品分类接口
 *
 * @author Ray Hao
 * @since 2024/04/20
 */
public interface CategoryService extends IService<Category> {


    /**
     * 分类列表（树形）
     *
     * @return 分类列表
     */
    List<CategoryVO> listCategories();

    /**
     * 分类列表（级联）
     *
     * @return 分类列表
     */
    List<Option> listCategoryOptions();


    /**
     * 保存（新增/修改）分类
     *
     * @param formData 分类表单数据
     * @return 分类ID
     */
    Long saveCategory( CategoryForm formData);

    /**
     * 删除分类
     *
     * @param id 分类ID
     */
    void deleteCategory(Long id);

    /**
     * 获取分类表单数据
     *
     * @param id 分类ID
     * @return 分类表单数据
     */
    CategoryForm getCategoryForm(Long id);

    /**
     * 获取APP端分类列表
     *
     * @return APP端分类列表
     */
    List<CategoryAppVO> listAppCategories();

    /**
     * 获取分类下的属性列表
     *
     * @param categoryId 分类ID
     * @param type       属性类型
     * @return 属性列表
     */
    List<Option> listAttributesByCategoryId(Long categoryId, AttributeTypeEnum type);
}
