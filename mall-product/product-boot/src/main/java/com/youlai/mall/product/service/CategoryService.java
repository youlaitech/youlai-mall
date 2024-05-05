package com.youlai.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.common.web.model.Option;
import com.youlai.mall.product.model.entity.Category;
import com.youlai.mall.product.model.form.CategoryForm;
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
     * @param parentId 父分类ID
     * @return
     */
    List<CategoryVO> listCategories(Long parentId);

    /**
     * 分类列表（级联）
     * @return
     */
    List<Option> listCategoryOptions();


    /**
     * 保存（新增/修改）分类
     *
     * @param formData 分类表单数据
     * @return
     */
    Long saveCategory( CategoryForm formData);

    /**
     * 删除分类
     *
     * @param id 分类ID
     * @return
     */
    boolean deleteCategory(Long id);
}
