package com.youlai.mall.pms.service;

import com.youlai.mall.pms.model.entity.SkuSpecValue;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.model.form.SkuSpecValueForm;
import com.youlai.mall.pms.model.query.SkuSpecValuePageQuery;
import com.youlai.mall.pms.model.vo.SkuSpecValuePageVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
/**
 * SKU规格值 服务类
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
public interface SkuSpecValueService extends IService<SkuSpecValue> {


    /**
     *SKU规格值分页列表
     *
     * @return
     */
    IPage<SkuSpecValuePageVO> listPagedSkuSpecValues(SkuSpecValuePageQuery queryParams);


    /**
     * 获取SKU规格值表单数据
     *
     * @param id SKU规格值ID
     * @return
     */
     SkuSpecValueForm getSkuSpecValueFormData(Long id);


    /**
     * 新增SKU规格值
     *
     * @param formData SKU规格值表单对象
     * @return
     */
    boolean saveSkuSpecValue(SkuSpecValueForm formData);

    /**
     * 修改SKU规格值
     *
     * @param id   SKU规格值ID
     * @param formData SKU规格值表单对象
     * @return
     */
    boolean updateSkuSpecValue(Long id, SkuSpecValueForm formData);


    /**
     * 删除SKU规格值
     *
     * @param idsStr SKU规格值ID，多个以英文逗号(,)分割
     * @return
     */
    boolean deleteSkuSpecValues(String idsStr);

}
