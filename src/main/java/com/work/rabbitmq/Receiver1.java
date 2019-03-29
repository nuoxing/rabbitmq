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
	 *测试 就是没有开启手动 ack 代码报到 信息 只是unacked状态而已
	 * @param msg
	 */
	//@RabbitHandler
    public void process(String msg){
		
        System.out.println("Receiver收到了消息:"+msg);
        String s = null;
		s.length();
       
    }
    
    /**
	 *测试 就是没有开启手动 ack 代码报到 信息 只是unacked状态而已 
	 *测试得出 就算开启手动ack 该函数执行完 还是消息被消费了
	 * @param msg
	 */
	//@RabbitHandler
    public void process2(String msg,com.rabbitmq.client.Channel channel,Message message){
		
        System.out.println("Receiver收到了消息:"+msg);
        try {
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
     /* try {
    	  System.out.println(Thread.currentThread().getName());
		Thread.sleep(20000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/
       
    }
	
	

}