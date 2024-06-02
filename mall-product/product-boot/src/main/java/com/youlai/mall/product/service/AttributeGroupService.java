package com.youlai.mall.product.service;

import com.youlai.common.web.model.Option;
import com.youlai.mall.product.model.entity.AttributeGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.product.model.form.AttributeGroupForm;
import com.youlai.mall.product.model.query.AttributeGroupPageQuery;
import com.youlai.mall.product.model.vo.AttributeGroupPageVO;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 属性组 服务类
 *
 * @author Ray Hao
 * @since 2024-04-19
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
     * @param ids 属性组ID，多个以英文逗号(,)分割
     */
    void deleteAttributeGroups(String ids);

    /**
     * 获取属性组选项列表
     *
     * @return
     */
    List<Option> listAttributeOptions(Long categoryId);
}
