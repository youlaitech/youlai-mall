package com.fly4j.yshop.pms.service.app;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fly4j.yshop.pms.pojo.dto.app.AppSpuDTO;
import com.fly4j.yshop.pms.pojo.entity.PmsSpu;

/**
 * @author haoxianrui
 * @since 2020-04-20
 **/
public interface IAppSpuService extends IService<PmsSpu> {
    AppSpuDTO getSpuDetail(Long id);
}
