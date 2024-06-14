package com.youlai.mall.product.service;

import com.youlai.mall.product.model.entity.AttrGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.product.model.form.AttrGroupForm;
import com.youlai.mall.product.model.query.AttrGroupPageQuery;
import com.youlai.mall.product.model.vo.AttrGroupPageVO;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 属性组 服务类
 *
 * @author Ray Hao
 * @since 2024-04-19
 */
public interface AttrGroupService extends IService<AttrGroup> {


    /**
     *属性组分页列表
     *
     * @return
     */
    IPage<AttrGroupPageVO> listPagedAttrGroups(AttrGroupPageQuery queryParams);

    /**
     * 新增属性组
     *
     * @param formData 属性组表单对象
     * @return
     */
    boolean saveAttributeGroup(AttrGroupForm formData);

    /**
     * 修改属性组
     *
     * @param id   属性组ID
     * @param formData 属性组表单对象
     * @return
     */
    boolean updateAttributeGroup(Long id, AttrGroupForm formData);


    /**
     * 删除属性组
     *
     * @param ids 属性组ID，多个以英文逗号(,)分割
     */
    void deleteAttributeGroups(String ids);



}
