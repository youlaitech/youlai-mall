package com.youlai.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.product.model.entity.Attr;
import com.youlai.mall.product.model.form.AttrForm;
import com.youlai.mall.product.model.vo.AttrGroupVO;
import com.youlai.mall.product.model.vo.AttrVO;

import java.util.List;

/**
 * 属性 服务类
 *
 * @author Ray Hao
 * @since 2024-04-19
 */
public interface AttrService extends IService<Attr> {

    /**
     * 根据分类ID获取属性列表
     *
     * @param categoryId 分类ID
     */
    List<AttrGroupVO> listAttrsByCategoryId(Long categoryId);
}
