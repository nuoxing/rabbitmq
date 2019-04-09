package com.work.rabbitmq;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Component
public class Receiver1 {

	/**
	 * 测试 没有开启手动 ack 代码报错 信息 unacked状态而已
	 * 理由：①自动确认模式，消费者挂掉，待ack的消息回归到队列中。消费者抛出异常，消息会不断的被重发，直到处理成功。不会丢失消息，即便服务挂掉，没有处理完成的消息会重回队列，但是异常会让
	 * 消息不断重试。
	 * 
	 * @param msg
	 */
	 //@RabbitHandler
	 //@RabbitListener(queues = "hello")
	public void process(String msg) {

		System.out.println("Receiver收到了消息:" + msg);

	}

	/**
	 * 收到信息 回滚 消息返回到队列中
	 * 
	 * @param
	 */
	// @RabbitHandler
	// @RabbitListener(queues = "hello")
	public void processAndToQueue(Message message, Channel channel) {

		System.out.println("Receiver收到了消息:" + message.getBody());

		// 确认信息
		try {
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 收到信息 并且ack确认
	 * 
	 * @param
	 */
	// @RabbitHandler
	//@RabbitListener(queues = "hello")
	public void processAndAck(Message message, Channel channel) {

		// 返回的字节数组
		System.out.println("Receiver收到了消息:" + message.getBody());

		// 确认信息
		try {
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	/**
	 * 
	 * @RabbitListener(bindings = {@QueueBinding(value = @Queue(value = "roberto.order.add", durable = "true", autoDelete = "false", exclusive = "false"), exchange = @Exchange(name = "roberto.order"))})
	 * 通过 @RabbitListener 的 bindings 属性声明 Binding（若 RabbitMQ 中不存在该绑定所需要的 Queue、Exchange、RouteKey 则自动创建，若存在（不是这里绑定创建的意思,例如其他创建了的bean获取启动了多个实例）则抛出异常）
	 * 所以 一般情况下 不使用这个方法绑定 
	 */
	//@RabbitHandler
   // @RabbitListener(bindings = {@QueueBinding(value = @Queue(value ="aaa", durable = "true", autoDelete = "false", exclusive = "true"), exchange = @Exchange(value = "fanoutExchange3",type="fanout"))})
	public void guangbo(Message message, Channel channel){
		// 返回的字节数组
		System.out.println("Receiver1收到了消息:" + message.getBody());

		// 确认信息
		try {
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}