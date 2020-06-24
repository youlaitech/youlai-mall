package com.fly4j.yshop.pms.listener;

import com.fly4j.yshop.pms.service.IPmsSkuService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class StockListener {

    @Resource
    private IPmsSkuService iPmsSkuService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "STOCK-QUEUE", durable = "true"),
            exchange = @Exchange(value = "STOCK-EXCHANGE", ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            key = {"stock.unlock"}
    ))
    public void unlock(String orderToken){
        this.iPmsSkuService.unlockSku(orderToken);
    }
}