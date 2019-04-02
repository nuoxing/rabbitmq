package com.work.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

//@Configuration
public class RabbitConfig {

	@Resource
	RabbitTemplate rabbitTemplate;

	/**
	 * 定制化amqp模版 可根据需要定制多个
	 * <p>
	 * <p>
	 * 此处为模版类定义 Jackson消息转换器 ConfirmCallback接口用于实现消息发送到RabbitMQ交换器后接收ack回调
	 * 即消息发送到exchange ack ReturnCallback接口用于实现消息发送到RabbitMQ 交换器，但无相应队列与交换器绑定时的回调
	 * 即消息发送不到任何一个队列中 ack
	 *
	 * @return the amqp template
	 */
	@Bean
	public AmqpTemplate amqpTemplate() {
		rabbitTemplate.setEncoding("UTF-8");
		// 开启returncallback
		rabbitTemplate.setMandatory(true);
		rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
			String correlationId = message.getMessageProperties().getCorrelationIdString();
		});
		// 消息确认 yml 需要配置 publisher-returns: true
		rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
			if (ack) {
				System.out.println("消息发送到exchange成功,id: {}");
			} else {
				System.out.println("消息发送到exchange失败,原因: {}" + cause);
			}
		});
		return rabbitTemplate;
	}

}