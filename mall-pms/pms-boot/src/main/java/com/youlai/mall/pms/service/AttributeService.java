package com.youlai.mall.pms.service;

import com.youlai.mall.pms.model.entity.Attribute;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.model.form.AttributeForm;
import com.youlai.mall.pms.model.query.AttributePageQuery;
import com.youlai.mall.pms.model.vo.AttributePageVO;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 属性 服务类
 *
 * @author Ray Hao
 * @since 2024-04-14
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
     * @param idsStr 属性ID，多个以英文逗号(,)分割
     * @return
     */
    boolean deleteAttributes(String idsStr);

}
