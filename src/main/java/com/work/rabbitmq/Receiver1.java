package com.work.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "hello")
public class Receiver1 {
    @RabbitHandler
    public void process(String msg){
        System.out.println("Receiver收到了消息:"+msg);
    }

}