package com.youlai.test.seata.service;

import com.youlai.test.seata.dto.OrderDTO;

/**
 * @author haoxr
 * @description TODO
 * @createTime 2021/3/13 11:16
 */
public interface IOrderService {

    boolean save(OrderDTO order);

    boolean saveWithGlobalTransactional(OrderDTO order);

}
