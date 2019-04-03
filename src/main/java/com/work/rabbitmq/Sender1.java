package com.work.rabbitmq;

import java.util.Date;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender1 {

	/**
	 * 注入AmqpTemplate，然后利用AmqpTemplate向一个名为hello的消息队列中发送消息。
	 */
	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void send() {
		String msg = "hello rabbitMQ:" + new Date() + " 你好，高级消息队列使用ing~";
		System.out.println("单对单发送参数。Sender发出了消息:" + msg);
		this.rabbitTemplate.convertAndSend("hello", msg);
	}

	/**
	 * 发送给默认的exchange rabbitmq 默认创建了8个exchange DirectExchanage 只有一个队列会接收
	 */
	public void sendDirectExchanage() {
		CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
		System.err.println("开始发送directExchanage类型的信息");
		Object msg = "hello rabbitMQ:" + new Date() + " 你好，高级消息队列使用ing~";
		System.out.println("单对单发送参数。Sender发出了消息:" + msg);

		this.rabbitTemplate.convertAndSend("hello", msg, correlationData);
		System.out.println("1");
	}

	/**
	 * 发送一个信息到 指定的exchange topic exchange 根据规则 匹配 绑定的队列
	 */
	public void sendtopicExchange() {
		this.rabbitTemplate.convertAndSend("exchange", "topic.message", "测试");
	}

	/**
	 * fanout exchange 忽略 routing key 只要跟该exchange绑定的队列 对接口到 该 消息
	 */
	public void sendfanoutExchange() {
		this.rabbitTemplate.convertAndSend("fanoutExchange", "", "测试");
	}

}