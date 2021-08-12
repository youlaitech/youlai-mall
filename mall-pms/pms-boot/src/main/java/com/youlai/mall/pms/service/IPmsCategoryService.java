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


    List<CategoryVO> listTreeCategory(Long parentId);

    List<CascadeVO> listCascadeCategory();
}
