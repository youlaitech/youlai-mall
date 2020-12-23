package com.youlai.mall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.pojo.PmsSpuAttr;

import java.util.List;

public interface IPmsAttrValueService extends IService<PmsSpuAttr> {

    List<PmsSpuAttr> listBySpuId(Long spuId);

}
