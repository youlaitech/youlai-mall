package com.fly4j.yshop.pms.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

	/**
	 * 定义延时队列
	 */
	@Bean("STOCK-TTL-QUEUE")
	public Queue ttlQueue(){
		//延时队列中的消息过期了，会自动触发消息的转发，通过指定routing-key发送到指定exchange中
		Map<String, Object> arguments = new HashMap<>();
		arguments.put("x-dead-letter-exchange", "STOCK-EXCHANGE");
		arguments.put("x-dead-letter-routing-key", "stock.unlock");
		arguments.put("x-message-ttl", 120000); // 单位:毫秒 2分钟仅仅用于测试，实际根据需求，通常30分钟或者15分钟
		return new Queue("STOCK-TTL-QUEUE", true, false, false, arguments);
	}


	/**
	 * 延时队列绑定到交换机
	 * rountingKey：store.create
	 */
	@Bean("STOCK-TTL-BINDING")
	public Binding ttlBinding(){
		// 锁定库存后会发送一条routingKey=stock.create的消息到STOCK-TTL-QUEUE,然后会被路由到延时队列STOCK-EXCHANGE,延时队列没有消费者,到期后会将消息转发
		return new Binding("STOCK-TTL-QUEUE", Binding.DestinationType.QUEUE, "STOCK-EXCHANGE", "stock.create", null);
	}

}
