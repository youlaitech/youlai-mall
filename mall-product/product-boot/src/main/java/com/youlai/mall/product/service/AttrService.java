package com.youlai.mall.product.service;

import com.youlai.mall.product.model.entity.Attr;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.product.model.form.AttrForm;
import com.youlai.mall.product.model.query.AttributePageQuery;
import com.youlai.mall.product.model.vo.AttributeGroupVO;
import com.youlai.mall.product.model.vo.AttrPageVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.mall.product.model.vo.AttributeVO;

import java.util.List;

/**
 * 属性 服务类
 *
 * @author Ray Hao
 * @since 2024-04-19
 */
public interface AttrService extends IService<Attr> {


    /**
     * 属性分页列表
     */
    IPage<AttrPageVO> listPagedAttributes(AttributePageQuery queryParams);


    /**
     * 获取属性表单数据
     *
     * @param id 属性ID
     */
    AttrForm getAttributeFormData(Long id);


    /**
     * 新增属性
     *
     * @param formData 属性表单对象
     */
    boolean saveAttribute(AttrForm formData);

    /**
     * 修改属性
     *
     * @param id       属性ID
     * @param formData 属性表单对象
     */
    boolean updateAttribute(Long id, AttrForm formData);

    /**
     * 删除属性
     *
     * @param ids 属性ID，多个以英文逗号(,)分割
     */
    boolean deleteAttributes(String ids);

    /**
     * 获取基础属性列表
     *
     * @param categoryId 商品分类ID
     */
    List<AttributeGroupVO> listAttributesByCategoryId(Long categoryId);

    /**
     * 获取销售属性列表
     *
     * @param categoryId 商品分类ID
     */
    List<AttributeVO> listSaleAttributes(Long categoryId);
}
