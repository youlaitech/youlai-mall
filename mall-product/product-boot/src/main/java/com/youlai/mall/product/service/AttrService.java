package com.youlai.mall.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.product.model.entity.Attr;
import com.youlai.mall.product.model.form.AttrForm;
import com.youlai.mall.product.model.query.AttrQuery;
import com.youlai.mall.product.model.vo.AttrVO;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 商品属性服务接口
 *
 * @author Ray.Hao
 * @since 2024-04-19
 */
public interface AttrService extends IService<Attr> {

    /**
     * 根据分类ID获取属性列表
     *
     * @param categoryId 分类ID
     */
    List<AttrVO> getCategoryAttrs(Long categoryId);

    /**
     * 保存属性
     *
     * @param formData 属性表单
     * @return 是否成功
     */
    boolean saveAttr(@Valid AttrForm formData);

    /**
     * 获取属性表单
     *
     * @param id 属性ID
     * @return 属性表单
     */
    AttrForm getAttrForm(Long id);

    /**
     * 更新属性
     *
     * @param id       属性ID
     * @param formData 属性表单
     * @return
     */
    boolean updateAttr(Long id, AttrForm formData);

    /**
     * 删除属性
     *
     * @param ids 属性ID
     */
    void deleteAttrs(String ids);
}
