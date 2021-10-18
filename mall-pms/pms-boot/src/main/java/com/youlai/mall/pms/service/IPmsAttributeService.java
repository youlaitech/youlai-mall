package com.youlai.mall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.pojo.dto.admin.AttributeFormDTO;
import com.youlai.mall.pms.pojo.entity.PmsAttribute;

public interface IPmsAttributeService extends IService<PmsAttribute> {

    boolean saveBatch(AttributeFormDTO attributeForm);
}
