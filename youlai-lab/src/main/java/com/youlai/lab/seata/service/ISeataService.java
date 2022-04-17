package com.youlai.lab.seata.service;

import com.youlai.lab.seata.pojo.vo.SeataDataVO;

/**
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/4/16 20:49
 */
public interface ISeataService {

    /**
     * 模拟订单提交
     *
     * @return
     */
    boolean payOrder();

    /**
     * 获取模拟数据
     *
     * @return
     */
    SeataDataVO getData();

    /**
     * 重置模拟数据
     *
     * @return
     */
    boolean resetData();
}
