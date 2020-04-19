package com.fly4j.yshop.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.yshop.oms.mapper.OmsOrderMapper;
import com.fly4j.yshop.oms.pojo.dto.OrderDTO;
import com.fly4j.yshop.oms.pojo.entity.OmsOrder;
import com.fly4j.yshop.oms.pojo.entity.OmsOrderItem;
import com.fly4j.yshop.oms.service.IOmsOrderItemService;
import com.fly4j.yshop.oms.service.IOmsOrderService;
import com.fly4j.yshop.pms.feign.PmsAppFeign;
import com.fly4j.yshop.pms.pojo.vo.SkuLockVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fly2021【xianrui0365@163.com】
 * @date 2020-04-13 15:02
 **/
@Service
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderMapper, OmsOrder> implements IOmsOrderService {

    @Resource
    private PmsAppFeign pmsAppFeign;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private IOmsOrderItemService iOmsOrderItemService;
    @Resource
    private AmqpTemplate amqpTemplate;

    private static final String TOKEN_PREFIX = "order:token:";



    /**
     * 提交订单
     * @param orderDTO
     * @return
     */

    @Override
    public R submit(OrderDTO orderDTO) {
        /**
         *  1. 验证令牌，是否重复提交
         */
        String orderToken = orderDTO.getOrder_token();
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Long flag = stringRedisTemplate.execute(new DefaultRedisScript<>(script, Long.class), Arrays.asList(TOKEN_PREFIX + orderToken), orderToken);
        if (flag == 0L) {
            return R.failed("订单不可重复提交");
        }

        /**
         * 2. 验证价格,防止用户在订单页面停留太久，导致的价格不一致
         */
        BigDecimal totalPrice = orderDTO.getOrder().getTotal_amount();
        List<OmsOrderItem> orderItems = orderDTO.getOrder_item_list();
        // 如果没有订单清单，直接返回
        if (CollectionUtils.isEmpty(orderItems)){
            return R.failed("请选择商品再提交");
        }

//        BigDecimal currentPrice = new BigDecimal(0);
//        for (OmsOrderItem orderItem : orderItems) {
//            R<PmsSku> skuInfoResp = pmsAppFeign.getSkuById(orderItem.getSku_id());
//            PmsSku skuInfo = skuInfoResp.getData();
//            BigDecimal decimal = skuInfo.getPrice().multiply(new BigDecimal(orderItem.getSku_quantity()));
//            currentPrice = currentPrice.add(decimal);
//        }
//        // 如果价格不同，直接返回
//        if (totalPrice.compareTo(currentPrice) != 0) {
//            return R.failed("页面已过期,请刷新后重试");
//        }

        /**
         * 3. 验库存，并锁库存
         */
        List<SkuLockVO> skuLockVOList = orderItems.stream().map(orderItem -> {
            SkuLockVO skuLockVO = new SkuLockVO();
            skuLockVO.setSku_id(orderItem.getSku_id());
            skuLockVO.setQuantity(orderItem.getSku_quantity());
            skuLockVO.setOrder_token(orderToken);
            return skuLockVO;
        }).collect(Collectors.toList());

        // 调用远程方法验证库存并锁库存
        R<SkuLockVO> SkuLockList = pmsAppFeign.checkAndLockStock(skuLockVOList);

        // 锁定失败
        if (SkuLockList.getCode() == 1) {
           return R.failed("锁定失败");
        }
        // 锁定成功，获取锁定的结果集

        /**
         * 4. 生成订单
         */
        try {
            // 创建订单，并定时关单 发送消息至死信队列  订单未支付时间过期订单状态关闭
            R<OmsOrder> order = this.saveOrder(orderDTO);
        } catch (Exception e) {
            e.printStackTrace();

            // 解锁库存, 发送消息
            this.amqpTemplate.convertAndSend("STOCK-EXCHANGE", "stock.unlock", orderToken);

            return R.failed("服务器错误,创建订单失败");
        }

        /**
         * 5.支付成功后减库存
         */
        /*try {
            for (OmsOrderItem orderItem : orderItems) {
                this.pmsAppFeign.minusStock(orderItem.getSku_id(),orderItem.getSku_quantity());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("订单[{}]支付异常");
        }*/
        return R.ok(null);
    }

    /**
     * 保存订单
     * @param orderDTO
     * @return
     */
    public R saveOrder(OrderDTO orderDTO) {

        // 保存订单
        OmsOrder order = new OmsOrder();
        BeanUtils.copyProperties(orderDTO.getOrder(), order);
        order.setStatus(0);
        this.save(order);

        // 保存订单详情
        List<OmsOrderItem> orderItems = orderDTO.getOrder_item_list();
        List<OmsOrderItem> orderItemList = orderItems.stream().map(item -> {
            item.setOrder_id(order.getId());
            item.setOrder_sn(order.getOrder_sn());
            return item;
        }).collect(Collectors.toList());
        this.iOmsOrderItemService.saveBatch(orderItemList);

        // 订单创建之后发送消息到延时队列进行定时关单
        amqpTemplate.convertAndSend("ORDER-EXCHANGE", "order.create", orderDTO.getOrder_token());
        return R.ok("保存成功");
    }

    @Override
    public String token() {
        // 随机生成唯一令牌，防止重复提交
        // 分布式id生成器，timeId适合做订单号
        String token = IdWorker.getTimeId();
        this.stringRedisTemplate.opsForValue().set(TOKEN_PREFIX + token, token);
        return this.stringRedisTemplate.opsForValue().get(TOKEN_PREFIX + token);
    }

    @Override
    public int closeOrder(String orderToken) {
        OmsOrder orderEntity = this.getOne(new QueryWrapper<OmsOrder>().eq("order_sn", orderToken));
        if (orderEntity!=null&& orderEntity.getStatus().equals(0)) {
            return this.baseMapper.updateStatus(orderToken, 4);
        }
        return 0;
    }
}