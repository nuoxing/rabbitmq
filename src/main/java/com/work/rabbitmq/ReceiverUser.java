package com.work.rabbitmq;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.work.entity.User;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ReceiverUser {

	
	/**
	 * 指定containerFactory 它 listener容器
	 * @param msg
	 */
    @RabbitHandler
	@RabbitListener(queues = "hello2",containerFactory ="jsonContainerFactory")
	public void process2(User msg) {

		System.out.println("Receiver收到了消息:" + msg);

	}


}