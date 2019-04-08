package com.work.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.entity.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * 测试多个rabbittemplate实例 实现不同的回调函数 说明:
 * 继承RabbitTemplate.ConfirmCallback接口，而ConfirmCallback接口是用来回调消息发送成功后的方法，
 * 当一个消息被成功写入到RabbitMQ服务端时，会自动的回调RabbitTemplate.ConfirmCallback接口内的confirm方法完成通知
 */
@Service
public class ProductService implements RabbitTemplate.ConfirmCallback {

	// rabbitmqtemplate是不是单例 所以不能在这使用注解注入
	private RabbitTemplate rabbitTemplate;

	public ProductService(RabbitTemplate rabbitTemplate) {
		System.out.println("调用了");
		this.rabbitTemplate = rabbitTemplate;
		this.rabbitTemplate.setConfirmCallback(this);
	}

	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		System.out.println(" 消息id:" + correlationData);
		if (ack) {
			System.out.println("消息发送确认成功");
		} else {
			System.out.println("消息发送确认失败:" + cause);
		}
	}

	public void save() {
		// 执行保存
		System.out.println("发送信息");
		Object msg = "hello rabbitMQ:" + new Date() + " 你好，高级消息队列使用ing~";
		String uuid = UUID.randomUUID().toString();
		CorrelationData correlationId = new CorrelationData(uuid);
		this.rabbitTemplate.convertAndSend("hello", msg, correlationId);
	}

	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * 使用Message对象发送消息 json格式的数据
	 */
	public void sendMessageByVo() throws Exception {
		User user = new User();
		user.setCerno("9ifdfd");
		user.setName("测试");
		Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(user))
				//设置消息持久化
				.setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
		message.getMessageProperties().setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,
				MessageProperties.CONTENT_TYPE_JSON);
		String uuid = UUID.randomUUID().toString();
		CorrelationData correlationId = new CorrelationData(uuid);
		rabbitTemplate.convertAndSend("hello2", message,correlationId);

	}
	
	/**
	 * 使用Message对象发送消息 json格式的数据
	 */
	public void sendUser() throws Exception {
		User user = new User();
		user.setCerno("9ifdfd");
		user.setName("测试");
		
		String uuid = UUID.randomUUID().toString();
		CorrelationData correlationId = new CorrelationData(uuid);
		rabbitTemplate.convertAndSend("hello2", user,correlationId);

	}
}
