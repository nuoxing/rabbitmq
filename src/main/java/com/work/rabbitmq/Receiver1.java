package com.work.rabbitmq;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "hello")
public class Receiver1 {
  
	
	
	/**
	 *测试 就是没有开启手动 ack 代码报错  信息 只是unacked状态而已
	 *理由：①自动确认模式，消费者挂掉，待ack的消息回归到队列中。消费者抛出异常，消息会不断的被重发，直到处理成功。不会丢失消息，即便服务挂掉，没有处理完成的消息会重回队列，但是异常会让
消息不断重试。
	 * @param msg
	 */
	@RabbitHandler
    public void process(String msg){
		
        System.out.println("Receiver收到了消息:"+msg);
      
       
    }
    
   
	
	

}