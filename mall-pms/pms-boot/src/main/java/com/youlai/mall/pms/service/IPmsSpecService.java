package com.youlai.mall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.pojo.entity.PmsSpec;

import java.util.List;

public interface IPmsSpecService extends IService<PmsSpec> {

    List<PmsSpec> listBySpuId(Long spuId);

}
