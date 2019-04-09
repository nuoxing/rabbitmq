package com.work.config;

import java.util.UUID;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.Channel;

@Configuration
public class RabbitConfigReceiver {

	/**
	 * 对rabbitmq服务器的连接
	 */
	@Autowired
	ConnectionFactory connectionFactory;

	/**
	 * 设置 消费接收json格式的数据 消费者监听器特性设置
	 * 
	 * @param configurer
	 * @param connectionFactory
	 * @return
	 */
	@Bean("jsonContainerFactory")
	public SimpleRabbitListenerContainerFactory pointTaskContainerFactory(
			SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setMessageConverter(new Jackson2JsonMessageConverter());
		configurer.configure(factory, connectionFactory);
		return factory;
	}

	@Bean
	public Queue randomQueue() {
		return new Queue(UUID.randomUUID().toString(),true,true,false);
	}

	@Bean
	FanoutExchange fanoutExchangerandom() {
		return new FanoutExchange("fanoutExchangerandom");
	}

	@Bean
	Binding bindingExchangeC(Queue randomQueue, FanoutExchange fanoutExchangerandom) {
		return BindingBuilder.bind(randomQueue).to(fanoutExchangerandom);
	}

	/**SimpleMessageListenerContainer 消费者容器
	 * 定义一个消费者  实现发布订阅模式 非注解方式实现消费者
	 * 定义一个 queue为排他性  断开连接时 自动删除的  而且与fanout exchange绑定
	 * 
	 * @return
	 */
	@Bean
	public SimpleMessageListenerContainer messageContainer() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		container.setQueues(randomQueue());// 队列
		container.setExposeListenerChannel(true);
		container.setMaxConcurrentConsumers(1);
		container.setConcurrentConsumers(1);
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL); // 设置确认模式手工确认
		container.setMessageListener(new ChannelAwareMessageListener() {

			@Override
			public void onMessage(Message message, Channel channel) throws Exception {
				// TODO Auto-generated method stub
				byte[] body = message.getBody();
				System.out.println("receive msg : " + new String(body));
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // 确认消息成功消费
			}
		});
		return container;
	}

}