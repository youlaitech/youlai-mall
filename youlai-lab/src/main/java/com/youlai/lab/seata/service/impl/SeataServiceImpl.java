package com.youlai.lab.seata.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.youlai.lab.seata.pojo.vo.SeataDataVO;
import com.youlai.lab.seata.service.ISeataService;
import com.youlai.mall.oms.api.OrderFeignClient;
import com.youlai.mall.oms.dto.OrderInfoDTO;
import com.youlai.mall.pms.api.SkuFeignClient;
import com.youlai.mall.pms.pojo.dto.SkuInfoDTO;
import com.youlai.mall.ums.api.MemberFeignClient;
import com.youlai.mall.ums.dto.MemberDTO;
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
    @GlobalTransactional
    public boolean payOrder() {

        log.info("扣减库存----begin");
        skuFeignClient.deductStock(skuId, 1); // 扣减库存
        log.info("扣减库存----end");

        log.info("扣减账户余额----begin");
        memberFeignClient.deductBalance(memberId, 1000 * 100); //扣款1000
        log.info("扣减账户余额----end");

        log.info("修改订单状态----begin");
        orderFeignClient.updateOrderStatus(orderId, 901); // 订单完结
        log.info("修改订单状态----end");

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
        SeataDataVO.SkuInfo skuInfo = new SeataDataVO.SkuInfo();
        BeanUtil.copyProperties(skuInfoDTO, skuInfo);
        skuInfo.setName(skuInfoDTO.getSkuName());
        seataDataVO.setSkuInfo(skuInfo);

        MemberInfoDTO memberInfoDTO = memberFeignClient.getMemberInfo(memberId).getData();
        SeataDataVO.MemberInfo memberInfo = new SeataDataVO.MemberInfo();
        BeanUtil.copyProperties(memberInfoDTO, memberInfo);
        seataDataVO.setMemberInfo(memberInfo);

        OrderInfoDTO orderInfoDTO = orderFeignClient.getOrderInfo(orderId).getData();
        SeataDataVO.OrderInfo orderInfo = new SeataDataVO.OrderInfo();
        BeanUtil.copyProperties(orderInfoDTO, orderInfo);
        seataDataVO.setOrderInfo(orderInfo);

        return seataDataVO;
    }

    /**
     * 重置模拟数据
     *
     * @return
     */
    @Override
    public boolean resetData() {

        log.info("扣减库存----begin");
        skuFeignClient.updateStock(skuId, 999); // 还原库存
        log.info("扣减库存----end");

        log.info("扣减账户余额----begin");
        memberFeignClient.updateBalance(memberId, 10000000 * 100); // 还原余额
        log.info("扣减账户余额----end");

        log.info("修改订单状态----begin");
        orderFeignClient.updateOrderStatus(orderId, 101); // 待支付
        log.info("修改订单状态----end");

        return true;

    }
}
