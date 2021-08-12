package com.youlai.mall.pms.serviceapp;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.pojo.entity.PmsSpu;
import com.youlai.mall.pms.pojo.vo.app.GoodsDetailVO;

/**
 * @author <a href="mailto:xianrui0365@163.com">xianrui</a>
 * @date 2021/8/8
 */
public interface IGoodsService extends IService<PmsSpu> {
    GoodsDetailVO getGoodsById(Long id);
}
