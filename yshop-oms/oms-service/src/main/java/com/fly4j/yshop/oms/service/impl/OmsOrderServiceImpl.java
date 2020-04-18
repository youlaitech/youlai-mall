package com.fly4j.yshop.oms.service.impl;

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
import com.fly4j.yshop.pms.pojo.entity.PmsSku;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

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
    private JedisPool jedisPool;
    @Resource
    private PmsAppFeign pmsAppFeign;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private IOmsOrderItemService iOmsOrderItemService;

    private static final String TOKEN_PREFIX = "order:token:";

    /**
     * 保存订单
     * @param orderDTO
     * @return
     */
    @Override
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
        return R.ok("保存成功");
    }

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
        String orderToken = orderDTO.getOrderToken();
        // 保证原子性（防止验证过程中，已下单）
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Jedis jedis = this.jedisPool.getResource();
        try {
            Long flag  = (Long) jedis.eval(script, Arrays.asList(TOKEN_PREFIX + orderToken), Arrays.asList(orderToken));

            // 验证未通过
//            if (flag == 0L) {
//                return R.failed("不可重复提交");
//            }
        } finally {
            jedis.close();
        }

        /**
         * 2. 验证价格,防止用户在订单页面停留太久，导致的价格不一致
         */
        BigDecimal totalPrice = orderDTO.getOrder().getTotal_amount();
        List<OmsOrderItem> orderItems = orderDTO.getOrder_item_list();
        // 如果没有订单清单，直接返回
        if (CollectionUtils.isEmpty(orderItems)){
            return R.failed("没有订单清单");
        }

        BigDecimal currentPrice = new BigDecimal(0);
        for (OmsOrderItem orderItem : orderItems) {
            R<PmsSku> skuInfoResp = this.pmsAppFeign.getSkuById(orderItem.getSku_id());
            PmsSku skuInfo = skuInfoResp.getData();
            BigDecimal decimal = skuInfo.getPrice().multiply(new BigDecimal(orderItem.getSku_quantity()));
            currentPrice = currentPrice.add(decimal);
        }
        // 如果价格不同，直接返回
        if (totalPrice.compareTo(currentPrice) != 0) {
            return R.failed("价格有误");
        }

        /**
         * 3. 验库存，并锁库存
         */

        /**
         * 4. 生成订单
         */

        return null;
    }

    @Override
    public String token() {
        // 随机生成唯一令牌，防止重复提交
        // 分布式id生成器，timeId适合做订单号
        String token = IdWorker.getTimeId();
        this.redisTemplate.opsForValue().set(TOKEN_PREFIX + token, token);

        return (String)this.redisTemplate.opsForValue().get(TOKEN_PREFIX + token);
    }
}