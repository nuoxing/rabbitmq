package com.work.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
 
import java.util.Date;
 
@Component
public class Sender1 {
    /**
     * 注入AmqpTemplate，然后利用AmqpTemplate向一个名为hello的消息队列中发送消息。
     */
    @Autowired
    private AmqpTemplate rabbitTemplate;
    public void send(){
        String msg="hello rabbitMQ:"+new Date()+" 你好，高级消息队列使用ing~";
        System.out.println("单对单发送参数。Sender发出了消息:"+msg);
        this.rabbitTemplate.convertAndSend("hello",msg);
        // 第一个参数表示交换机，第二个参数表示routing key，第三个参数即消息,Topic方式
        // this.rabbitTemplate.convertAndSend("topicExchange","key.da",msg);
    }

}