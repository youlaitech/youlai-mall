package com.youlai.mall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.pojo.PmsAttrValue;

import java.util.List;

public interface IPmsAttrValueService extends IService<PmsAttrValue> {

    List<PmsAttrValue> listBySpuId(Long spuId);

}
