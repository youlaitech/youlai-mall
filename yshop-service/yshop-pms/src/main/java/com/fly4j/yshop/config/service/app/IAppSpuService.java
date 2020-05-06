package com.fly4j.yshop.pms.service.app;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fly4j.yshop.pms.pojo.entity.PmsSpu;
import com.fly4j.yshop.pms.pojo.dto.app.AppGoodsDetailDTO;

/**
 * @author haoxianrui
 * @since 2020-04-20
 **/
public interface IAppSpuService extends IService<PmsSpu> {

    AppGoodsDetailDTO getGoodsDetail(Long id);
}
