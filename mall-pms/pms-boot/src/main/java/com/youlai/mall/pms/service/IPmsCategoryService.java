package com.youlai.mall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.common.pojo.vo.CascadeVO;
import com.youlai.mall.pms.pojo.entity.PmsCategory;
import com.youlai.mall.pms.pojo.vo.CategoryVO;

import java.util.List;


/**
 * @author <a href="mailto:xianrui0365@163.com">xianrui</a>
 */
public interface IPmsCategoryService extends IService<PmsCategory> {


    /**
     * 分类列表（树形）
     *
     * @param parentId
     * @return
     */
    List<CategoryVO> listCategory(Long parentId);

    /**
     * 分类列表（级联）
     * @return
     */
    List<CascadeVO> listCascadeCategory();
}
