package com.work.config;

import javax.annotation.Resource;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.converter.MessageConverter;

import javax.annotation.Resource;

@Configuration
public class RabbitConfigSender {


	//@Resource
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
	//@Bean
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
	
	
	 /**
     * 因为要设置回调类，所以应是prototype类型，如果是singleton类型，则回调类为最后一次设置
     */
     @Bean
     @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate(ConnectionFactory  connectionFactory) {
        RabbitTemplate rabbitTemplate =  new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());//设置消息转换器 发送json格式的数据
        return rabbitTemplate;
    }

 
}
