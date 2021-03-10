package com.youlai.mall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.pojo.domain.PmsSpecification;

import java.util.List;

public interface IPmsSpecificationService extends IService<PmsSpecification> {

    List<PmsSpecification> listBySpuId(Long spuId);

}
