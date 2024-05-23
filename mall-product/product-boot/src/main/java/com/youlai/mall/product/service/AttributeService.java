package com.youlai.mall.product.service;

import com.youlai.common.web.model.Option;
import com.youlai.mall.product.model.entity.Attribute;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.product.model.form.AttributeForm;
import com.youlai.mall.product.model.query.AttributePageQuery;
import com.youlai.mall.product.model.vo.AttributePageVO;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 属性 服务类
 *
 * @author Ray Hao
 * @since 2024-04-19
 */
public interface AttributeService extends IService<Attribute> {


    /**
     * 属性分页列表
     *
     * @return
     */
    IPage<AttributePageVO> listPagedAttributes(AttributePageQuery queryParams);


    /**
     * 获取属性表单数据
     *
     * @param id 属性ID
     * @return
     */
    AttributeForm getAttributeFormData(Long id);


    /**
     * 新增属性
     *
     * @param formData 属性表单对象
     * @return
     */
    boolean saveAttribute(AttributeForm formData);

    /**
     * 修改属性
     *
     * @param id       属性ID
     * @param formData 属性表单对象
     * @return
     */
    boolean updateAttribute(Long id, AttributeForm formData);


    /**
     * 删除属性
     *
     * @param ids 属性ID，多个以英文逗号(,)分割
     * @return
     */
    boolean deleteAttributes(String ids);

    /**
     * 根据分类ID获取属性组&列表
     *
     * @param categoryId 分类ID
     * @return
     */
    List<Option> listAttributesWithGroupByCategoryId(Long categoryId);
}
