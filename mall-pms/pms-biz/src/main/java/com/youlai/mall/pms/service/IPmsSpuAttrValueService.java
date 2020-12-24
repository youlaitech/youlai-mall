package com.youlai.mall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.pojo.PmsSpuAttrValue;

import java.util.List;

public interface IPmsSpuAttrValueService extends IService<PmsSpuAttrValue> {

    List<PmsSpuAttrValue> listBySpuId(Long spuId);

}
