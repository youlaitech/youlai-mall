package com.youlai.mall.pms.service;

import com.youlai.mall.pms.model.entity.AttributeGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.model.form.AttributeGroupForm;
import com.youlai.mall.pms.model.query.AttributeGroupPageQuery;
import com.youlai.mall.pms.model.vo.AttributeGroupPageVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
/**
 * 属性组接口
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
public interface AttributeGroupService extends IService<AttributeGroup> {


    /**
     *属性组分页列表
     *
     * @return
     */
    IPage<AttributeGroupPageVO> listPagedAttributeGroups(AttributeGroupPageQuery queryParams);


    /**
     * 获取属性组表单数据
     *
     * @param id 属性组ID
     * @return
     */
     AttributeGroupForm getAttributeGroupFormData(Long id);


    /**
     * 新增属性组
     *
     * @param formData 属性组表单对象
     * @return
     */
    boolean saveAttributeGroup(AttributeGroupForm formData);

    /**
     * 修改属性组
     *
     * @param id   属性组ID
     * @param formData 属性组表单对象
     * @return
     */
    boolean updateAttributeGroup(Long id, AttributeGroupForm formData);


    /**
     * 删除属性组
     *
     * @param idsStr 属性组ID，多个以英文逗号(,)分割
     * @return
     */
    boolean deleteAttributeGroups(String idsStr);

}
