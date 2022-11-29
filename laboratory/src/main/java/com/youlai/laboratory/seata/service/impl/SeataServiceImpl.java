package com.youlai.laboratory.seata.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.youlai.common.result.Result;
import com.youlai.laboratory.seata.pojo.form.SeataForm;
import com.youlai.laboratory.seata.pojo.vo.SeataVO;
import com.youlai.laboratory.seata.service.SeataService;
import com.youlai.mall.oms.api.OrderFeignClient;
import com.youlai.mall.oms.dto.OrderInfoDTO;
import com.youlai.mall.oms.dto.SeataOrderDTO;
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
 * @author haoxr
 * @date 2022/4/16 20:49
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SeataServiceImpl implements SeataService {

    private final SkuFeignClient skuFeignClient;
    private final OrderFeignClient orderFeignClient;
    private final MemberFeignClient memberFeignClient;

    private static Long skuId = 1l; // SkuID
    private static Long memberId = 1l; // 会员ID
    private static Long orderId = 1l; // 订单ID

    /**
     * 获取模拟数据
     *
     * @return
     */
    @Override
    public SeataVO getData() {
        SeataVO seataVO = new SeataVO();

        SkuInfoDTO skuInfoDTO = skuFeignClient.getSkuInfo(skuId).getData();
        SeataVO.StockInfo stockInfo = new SeataVO.StockInfo();
        BeanUtil.copyProperties(skuInfoDTO, stockInfo);
        stockInfo.setName(skuInfoDTO.getSkuName());
        seataVO.setStockInfo(stockInfo);

        MemberInfoDTO memberInfoDTO = memberFeignClient.getMemberInfo(memberId).getData();
        SeataVO.AccountInfo accountInfo = new SeataVO.AccountInfo();
        BeanUtil.copyProperties(memberInfoDTO, accountInfo);
        seataVO.setAccountInfo(accountInfo);

        OrderInfoDTO orderInfoDTO = orderFeignClient.getOrderInfo(orderId).getData();
        SeataVO.OrderInfo orderInfo = new SeataVO.OrderInfo();
        BeanUtil.copyProperties(orderInfoDTO, orderInfo);
        seataVO.setOrderInfo(orderInfo);

        return seataVO;
    }

    /**
     * 重置还原数据
     *
     * @return
     */
    @Override
    public boolean resetData() {
        skuFeignClient.updateStock(skuId, 999); // 还原库存
        orderFeignClient.updateOrderStatus(orderId, 101, false); // 待支付
        return true;

    }

    /**
     * 购买商品
     *
     * @return 订单号
     */
    @Override
    public String purchaseGoods(SeataForm seataForm) {
        log.info("========扣减商品库存(全局事务)========");
        skuFeignClient.deductStock(seataForm.getSkuId(), 1); // 扣减库存

        log.info("========创建订单(全局事务)========");
        SeataOrderDTO seataOrderDTO = new SeataOrderDTO(
                seataForm.getMemberId(),
                seataForm.getSkuId(),
                seataForm.getAmount()
        );
        boolean openEx = seataForm.isOpenEx(); // 是否开启异常
        Result<String> result = orderFeignClient.createOrder(seataOrderDTO, openEx);
        String orderSn = result.getData();

        return orderSn;
    }

    /**
     * 购买商品(全局事务)
     *
     * @return 订单号
     */
    @GlobalTransactional
    @Override
    public String purchaseGoodsWithGlobalTx(SeataForm seataForm) {

        log.info("========扣减商品库存(全局事务)========");
        skuFeignClient.deductStock(seataForm.getSkuId(), 1); // 扣减库存

        log.info("========创建订单(全局事务)========");
        SeataOrderDTO seataOrderDTO = new SeataOrderDTO(
                seataForm.getMemberId(),
                seataForm.getSkuId(),
                seataForm.getAmount()
        );
        boolean openEx = seataForm.isOpenEx(); // 是否开启异常
        Result<String> result = orderFeignClient.createOrder(seataOrderDTO, openEx);
        String orderSn = result.getData();

        return orderSn;
    }
}
