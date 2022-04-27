package com.youlai.lab.seata.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.youlai.lab.seata.pojo.form.SeataForm;
import com.youlai.lab.seata.pojo.vo.SeataDataVO;
import com.youlai.lab.seata.service.ISeataService;
import com.youlai.mall.oms.api.OrderFeignClient;
import com.youlai.mall.oms.dto.OrderInfoDTO;
import com.youlai.mall.pms.api.SkuFeignClient;
import com.youlai.mall.pms.pojo.dto.SkuInfoDTO;
import com.youlai.mall.ums.api.MemberFeignClient;
import com.youlai.mall.ums.dto.MemberInfoDTO;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Seata实验室业务类接口
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/4/16 20:49
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SeataServiceImpl implements ISeataService {

    private final SkuFeignClient skuFeignClient;
    private final OrderFeignClient orderFeignClient;
    private final MemberFeignClient memberFeignClient;

    private static Long skuId = 1l; // SkuID
    private static Long memberId = 1l; // 会员ID
    private static Long orderId = 1l; // 订单ID

    /**
     * 模拟订单支付
     *
     * @return
     */
    @Override
    public boolean payOrder(SeataForm seataForm) {

        log.info("========扣减商品库存========");
        skuFeignClient.deductStock(skuId, 1); // 扣减库存

        log.info("========扣减账户余额========");
        memberFeignClient.deductBalance(memberId, 1000 * 100l); // 扣款1000

        log.info("========修改订单状态========");
        orderFeignClient.updateOrderStatus(orderId, 201, seataForm.isOrderEx()); // 已支付

        return true;
    }

    /**
     * 模拟订单支付(分布式事务)
     *
     * @param seataForm
     * @return
     */
    @Override
    @GlobalTransactional
    public boolean payOrderWithGlobalTx(SeataForm seataForm) {
        log.info("========扣减商品库存(Seata)========");
        skuFeignClient.deductStock(skuId, 1); // 扣减库存

        log.info("========扣减账户余额(Seata)========");
        memberFeignClient.deductBalance(memberId, 1000 * 100l); // 扣款1000

        log.info("========修改订单状态(Seata)========");
        orderFeignClient.updateOrderStatus(orderId, 201, seataForm.isOrderEx()); // 已支付

        return true;
    }

    /**
     * 获取模拟数据
     *
     * @return
     */
    @Override
    public SeataDataVO getData() {
        SeataDataVO seataDataVO = new SeataDataVO();

        SkuInfoDTO skuInfoDTO = skuFeignClient.getSkuInfo(skuId).getData();
        SeataDataVO.StockInfo stockInfo = new SeataDataVO.StockInfo();
        BeanUtil.copyProperties(skuInfoDTO, stockInfo);
        stockInfo.setName(skuInfoDTO.getSkuName());
        seataDataVO.setStockInfo(stockInfo);

        MemberInfoDTO memberInfoDTO = memberFeignClient.getMemberInfo(memberId).getData();
        SeataDataVO.AccountInfo accountInfo = new SeataDataVO.AccountInfo();
        BeanUtil.copyProperties(memberInfoDTO, accountInfo);
        seataDataVO.setAccountInfo(accountInfo);

        OrderInfoDTO orderInfoDTO = orderFeignClient.getOrderInfo(orderId).getData();
        SeataDataVO.OrderInfo orderInfo = new SeataDataVO.OrderInfo();
        BeanUtil.copyProperties(orderInfoDTO, orderInfo);
        seataDataVO.setOrderInfo(orderInfo);

        return seataDataVO;
    }

    /**
     * 重置还原数据
     *
     * @return
     */
    @Override
    public boolean resetData() {
        skuFeignClient.updateStock(skuId, 999); // 还原库存
        memberFeignClient.updateBalance(memberId, 10000000 * 100); // 还原余额
        orderFeignClient.updateOrderStatus(orderId, 101, false); // 待支付
        return true;

    }
}
