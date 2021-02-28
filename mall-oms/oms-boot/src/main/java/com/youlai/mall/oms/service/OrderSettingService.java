package com.youlai.mall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.common.mybatis.utils.PageUtils;
import com.youlai.mall.oms.pojo.entity.OrderSettingEntity;

import java.util.Map;

/**
 * 订单配置信息
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
public interface OrderSettingService extends IService<OrderSettingEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

