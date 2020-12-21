package com.youlai.mall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.pojo.PmsSpecification;

import java.util.List;

public interface IPmsSpecificationService extends IService<PmsSpecification> {

    List<PmsSpecification> listBySpuId(Long spuId);

}