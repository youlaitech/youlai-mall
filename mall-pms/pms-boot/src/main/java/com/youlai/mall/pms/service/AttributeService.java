package com.youlai.mall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.model.entity.PmsCategoryAttribute;
import com.youlai.mall.pms.model.form.PmsCategoryAttributeForm;

public interface AttributeService extends IService<PmsCategoryAttribute> {

    /**
     * 批量保存商品属性
     *
     * @param formData 属性表单数据
     * @return
     */
    boolean saveBatch(PmsCategoryAttributeForm formData);
}
