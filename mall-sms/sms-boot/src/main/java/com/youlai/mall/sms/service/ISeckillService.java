package com.youlai.mall.sms.service;

import com.youlai.mall.sms.pojo.vo.SmsSeckillSkuVO;

import java.util.List;

/**
 * @author huawei
 * @desc 秒杀模块业务接口
 * @email huawei_code@163.com
 * @date 2021/3/5
 */
public interface ISeckillService {

    void updateSeckillSkuLatest3Days();

    /**
     * 获取当前时间秒杀活动商品列表
     * @return
     */
    List<SmsSeckillSkuVO> getCurrentSeckillSession();

}
