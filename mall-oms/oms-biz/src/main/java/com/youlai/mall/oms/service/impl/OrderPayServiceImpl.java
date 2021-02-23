package com.youlai.mall.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.base.Query;
import com.youlai.common.mybatis.utils.PageUtils;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import com.youlai.common.web.util.WebUtils;
import com.youlai.mall.oms.dao.OrderPayDao;
import com.youlai.mall.oms.pojo.entity.OrderEntity;
import com.youlai.mall.oms.pojo.entity.OrderPayEntity;
import com.youlai.mall.oms.pojo.vo.PayInfoVO;
import com.youlai.mall.oms.service.OrderPayService;
import com.youlai.mall.oms.service.OrderService;
import com.youlai.mall.ums.api.MemberFeignService;
import com.youlai.mall.ums.pojo.dto.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;


@Slf4j
@AllArgsConstructor
@Service("orderPayService")
public class OrderPayServiceImpl extends ServiceImpl<OrderPayDao, OrderPayEntity> implements OrderPayService {

    private OrderService orderService;

    private MemberFeignService memberFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderPayEntity> page = this.page(
                new Query<OrderPayEntity>().getPage(params),
                new QueryWrapper<OrderPayEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PayInfoVO info(String orderId) {
        Long userId = WebUtils.getUserId();
        PayInfoVO payInfoVO = new PayInfoVO();
        // 1、获取订单应支付金额
        OrderEntity orderEntity = orderService.getByOrderId(orderId);
        payInfoVO.setPayPrice(orderEntity.getPayAmount());

        // 2、获取会员余额
        try {
            Result<MemberDTO> memberInfo = memberFeignService.getUserById(WebUtils.getUserId());
            if (memberInfo != null && memberInfo.getCode().equals(ResultCode.SUCCESS.getCode())) {
                MemberDTO data = memberInfo.getData();
                if (data != null) {
                    payInfoVO.setBalance(data.getBalance());
                } else {
                    log.error("获取会员信息失败,userId={}", userId);
                }
            }
        } catch (Exception e) {
            log.error("获取会员余额失败,userId={}", userId, e);
        }

        return payInfoVO;
    }

}