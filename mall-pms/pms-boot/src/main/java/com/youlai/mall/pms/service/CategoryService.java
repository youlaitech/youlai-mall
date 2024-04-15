package com.youlai.mall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.common.web.model.Option;
import com.youlai.mall.pms.model.entity.Category;
import com.youlai.mall.pms.model.vo.CategoryVO;

import java.util.List;


/**
 * 商品分类
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 */
public interface CategoryService extends IService<Category> {


    /**
     * 分类列表（树形）
     *
     * @param parentId
     * @return
     */
    List<CategoryVO> getCategoryList(Long parentId);

    /**
     * 分类列表（级联）
     * @return
     */
    List<Option> getCategoryOptions();


    /**
     *
     * 保存（新增/修改）分类
     *
     *
     * @param category
     * @return
     */
    Long saveCategory(Category category);

}
